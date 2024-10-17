package com.fullstack405.bitcfinalprojectkotlin.templete.event

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.CheckedIdData
import com.fullstack405.bitcfinalprojectkotlin.data.EventDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.QRscanData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEventDetailBinding
import com.fullstack405.bitcfinalprojectkotlin.databinding.DialogAdduserBinding
import com.fullstack405.bitcfinalprojectkotlin.databinding.DialogQrInfoBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.QR.CustomCaptureActivity
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private val CAMERA_REQUEST_CODE = 1001
    private var eventId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        eventId = intent.getLongExtra("eventId",0)
        var isRegistrationOpen = intent?.getCharExtra("isRegistrationOpen",'N') // 행사신청 마감 Y : 진행중 , N:마감
        var userId = intent?.getLongExtra("userId",0) // 접속자Id

        // 회원인지 아닌지만 판단
        var userPermission = intent?.getStringExtra("userPermission")

        binding.btnQRscanner.isVisible = false
        binding.btnAddAppUser.isVisible = false
        if (!userPermission.equals("정회원")) { // 정회원이 아니면
            binding.run{
                // 스캐너 버튼, 클릭 이벤트 오픈
                btnQRscanner.isVisible = true
                btnQRscanner.setOnClickListener {
                    checkCameraPermission()
                }

                // 추가하기 버튼, 클릭 이벤트 오픈
                btnAddAppUser.isVisible = true
                btnAddAppUser.setOnClickListener {
                    val dialogAdd = DialogAdduserBinding.inflate(LayoutInflater.from(this@EventDetailActivity))
                    var userAccount = ""
                    AlertDialog.Builder(this@EventDetailActivity).run {
                        setTitle("참석 인원 추가하기")
                        setView(dialogAdd.root)
                        dialogAdd.btnSearch.setOnClickListener {
                            val account = dialogAdd.editId.text.toString()
                            Client.retrofit.CheckedId(account).enqueue(object:retrofit2.Callback<CheckedIdData>{
                                override fun onResponse(call: Call<CheckedIdData>,response: Response<CheckedIdData>) {
                                    if(response.body() == null){
                                        dialogAdd.txtName.text = "일치하는 회원이 없습니다."
                                        dialogAdd.txtPhone.text = ""
                                    }
                                    else{
                                        val data = response.body() as CheckedIdData
                                        dialogAdd.txtName.text = "이름 : ${data.name}"
                                        dialogAdd.txtPhone.text = "핸드폰 번호 : ${data.userPhone}"
                                        userAccount = data.userAccount
                                    }
                                }

                                override fun onFailure(call: Call<CheckedIdData>, t: Throwable) {
                                    Log.d("btnAddAppUser error","eventDetail CheckedId error ${t.message}")
                                }
                            }) // checkedID
                        }// btnSearch dialog

                        setPositiveButton("추가",object :DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                if(userAccount != null){
                                    Client.retrofit.adminAppDirect(eventId!!, userAccount).enqueue(object:retrofit2.Callback<Int>{
                                        override fun onResponse(call: Call<Int>,response: Response<Int>) {
                                            if(response.body() == 2){
                                                Toast.makeText(this@EventDetailActivity,"추가 신청이 완료되었습니다. QR을 확인해주세요.",Toast.LENGTH_SHORT).show()
                                            }
                                            else{
                                                Toast.makeText(this@EventDetailActivity,"이미 신청한 회원입니다. 다시 확인해주세요.",Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onFailure(call: Call<Int>, t: Throwable) {
                                            Log.d("btnAddAppUser error","eventDetail setPositiveButton error ${t.message}")
                                        }

                                    })
                                }

                            }
                        }) // positiveBtn
                        setNegativeButton("취소",null)
                        show()
                    } //추가하기 dialog
                }

            }
        } // if permission

        val cal = Calendar.getInstance()
        cal.time = Date() // 오늘 날짜
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dateFormat.format(cal.time)

        lateinit var event:EventDetailData
        var url = "http://10.100.105.168:8080/eventImg/"
//        var posterName = event.eventPoster
        
//        이벤트id로 해당 이벤트 정보만 불러오기
        Client.retrofit.findEventId(eventId!!).enqueue(object:retrofit2.Callback<EventDetailData>{
            override fun onResponse(call: Call<EventDetailData>, response: Response<EventDetailData>) {
                Log.d("findEventId","${response.body()}")
                event = response.body() as EventDetailData
                binding.dTitle.text = event.eventTitle
                binding.dContent.text = event.eventContent
                binding.dWriter.text = event.posterUserName
                binding.dCreateDate.text = event.visibleDate

                // 이미지
                Glide.with(this@EventDetailActivity)
                    .load(url+event.eventPoster)
                    .into(binding.dImage)

                var endDate = event.schedules[event.schedules.size-1].get("eventDate").toString()
                // 관리자로 접속해서 qr 스캐너가 보인다면 마지막날 다음날 scanner 버튼 비활성화
                if(binding.btnQRscanner.isVisible && !userPermission.equals("정회원")){
                    if(endDate < today){
                        binding.btnQRscanner.isEnabled = false
                    }

                }

            } // onResponse
            override fun onFailure(call: Call<EventDetailData>, t: Throwable) {
                Log.d("eventDetail error","eventDetail load error")
            }
        }) // retrofit


        binding.btnSubmit.isEnabled = false
        // 신청버튼
        // 행사 신청 마감여부에 따라 활성화 비활성화
        if(isRegistrationOpen == 'Y'){
            binding.btnSubmit.isEnabled = true
            binding.btnSubmit.setOnClickListener {
                // 확인 다이얼로그
                AlertDialog.Builder(this).run{
                    setMessage("해당 행사에 참석하시겠습니까?")
                    setPositiveButton("확인",object:DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            // db 연결버전
                            Client.retrofit.insertEventApp(eventId, userId!!).enqueue(object:retrofit2.Callback<Int>{
                                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                                    Log.d("insert num","${response.body()}")
                                    AlertDialog.Builder(this@EventDetailActivity).run {
                                        if(response.body() == 2){
                                            setMessage("신청 완료되었습니다.")
                                        }
                                        else{
                                            setMessage("이미 신청한 행사입니다.")
                                        }

                                        setNegativeButton("닫기",null)
                                        show()
                                    }
                                    setResult(RESULT_OK)
                                }

                                override fun onFailure(call: Call<Int>, t: Throwable) {
                                    Log.d("insert error","${t.message}")
                                }
                            }) // retrofit
                        }// onclick
                    }) // positive
                    setNegativeButton("취소",null)
                    show()
                }
            }
        }else{
            binding.btnSubmit.isEnabled = false
            binding.btnSubmit.setBackgroundColor(Color.parseColor("#D5D5D5"))
        }
        // 뒤로가기
        binding.btnBack.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

    }// onCreate


    // 카메라 권한 확인
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        } else {
            startQRCodeScanner() // 권한이 이미 있으면 스캐너 시작
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startQRCodeScanner() // 권한이 허용되면 스캐너 시작
            } else {
                Log.d("barcode scanner","none permission")
            }
        }
    }



    // 스캐너 실행
    private fun startQRCodeScanner() {
        Log.d("barcode scanner","startQRCodeScanner")
        val intent = Intent(this, CustomCaptureActivity::class.java)
        barcodeLauncher.launch(intent)
    }

    // 인텐트 결과값 처리
    private val barcodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val scanResult = result.data?.getStringExtra("SCAN_RESULT")
            binding.btnSubmit.text = scanResult ?: "스캔 실패"

            val qr_eventId = scanResult!!.substring(0,1).toLong()
            val qr_scheduleId = scanResult.substring(2,3).toLong()
            val qr_userId = scanResult.substring(4).toLong()

            if(qr_eventId != eventId){
                AlertDialog.Builder(this@EventDetailActivity).run{
                    setMessage("해당 행사와 일치하지 않는 QR 입니다. 다시 확인해주세요.")
                    setNegativeButton("닫기", null)
                    show()
                }
                return@registerForActivityResult
            }

            Client.retrofit.insertQRCheck(qr_eventId, qr_scheduleId, qr_userId).enqueue(object:retrofit2.Callback<QRscanData>{
                override fun onResponse(call: Call<QRscanData>, response: Response<QRscanData>) {
                    val data = response.body() as QRscanData
                    val dialogQr = DialogQrInfoBinding.inflate(LayoutInflater.from(this@EventDetailActivity))
                    Log.d("insertQRCheck","${response.body()}")
                    AlertDialog.Builder(this@EventDetailActivity).run {
                        setView(dialogQr.root)
                        dialogQr.eventName.text = "행사명 : ${data.eventTitle}"
                        dialogQr.eventDate.text =
                            "일자 : ${data.eventDate}  |  ${data.startTime}~${data.endTime}"
                        dialogQr.userName.text = "이름 : ${data.name}"
                        dialogQr.userPhone.text = "휴대폰 : ${data.userPhone}"
                        dialogQr.userIn.text = "입장시간 : ${data.checkInTime}"
                        if (data.checkoutTime == null) {
                            dialogQr.userOut.text = "퇴장시간 : "
                        } else {
                            dialogQr.userOut.text = "퇴장시간 : ${data.checkoutTime}"
                        }
                        setNegativeButton("닫기", null)
                        show()
                    }

                } // onResponse

                override fun onFailure(call: Call<QRscanData>, t: Throwable) {
                    AlertDialog.Builder(this@EventDetailActivity).run {
                        setMessage("이미 처리된 QR입니다. 다시 확인해주세요.")
                        setNegativeButton("닫기", null)
                        show()
                    }
                    Log.d("insertQRCheck", "${t.message}")
                }
            })
        }
    }
}