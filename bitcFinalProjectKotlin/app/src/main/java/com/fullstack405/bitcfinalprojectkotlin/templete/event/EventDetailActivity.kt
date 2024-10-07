package com.fullstack405.bitcfinalprojectkotlin.templete.event

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.AttendInfoData
import com.fullstack405.bitcfinalprojectkotlin.data.EventAppData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEventDetailBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.QR.QrScannerActivity
import retrofit2.Call
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_event_detail)
        val binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var eventId = intent.getLongExtra("eventId",0)
        var title = intent.getStringExtra("eventTitle")
        var content = intent.getStringExtra("eventContent")
        var date = intent.getStringExtra("visibleDate") // 게시일
        var writer = intent.getLongExtra("writerId",0)// 작성자Id

        var userId = intent.getLongExtra("userId",0) // 접속자Id
        // 회원인지 아닌지만 판단
        var userPermission = intent.getStringExtra("userPermission")

        binding.btnQRscanner.isVisible = false

        if(!userPermission.equals("정회원")){
            binding.btnQRscanner.isVisible = true
            // 스캐너 버튼
            binding.btnQRscanner.setOnClickListener {
                // 카메라 화면 ?
//                Toast.makeText(this,"스캐너 버튼 클릭 이벤트",Toast.LENGTH_SHORT).show()

                var intentQrScanner = Intent(this,QrScannerActivity::class.java)
                startActivity(intentQrScanner)
            }

        }

//        lateinit var event: EventData
//        이벤트id로 해당 이벤트 정보만 불러오기
//        Client.retrofit.findEventId(eventId).enqueue(object:retrofit2.Callback<EventData>{
//            override fun onResponse(call: Call<EventData>, response: Response<EventData>) {
//                event = response.body()!!
//            }
//
//            override fun onFailure(call: Call<EventData>, t: Throwable) {
//                Log.d("eventDetail error","eventDetail load error")
//            }
//        }) // retrofit

//        binding.dTitle.text = event.eventTitle
//        binding.dContent.text = event.eventContent
//        binding.dCreateDate.text=event.visibleDate

        // 유저id로 회원 정보(작성자) 찾아오기
//        Client.retrofit.findUserId(event.userId).enqueue(object:retrofit2.Callback<UserData>{
//            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
//                var data = response.body() as UserData
//                binding.dWriter.text = data.userName
//            }
//
//            override fun onFailure(call: Call<UserData>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

        binding.dTitle.text = title
        binding.dCreateDate.text = date // 게시일
        binding.dContent.text = content


//        var url = "http://10.100.105.194:8811/events/"
//        var posterName = event.eventPoster

        var url = "https://godomall.speedycdn.net/246ac6860cab30de5414f7d17e2bb4bc/editor/board/event/453/730baf1671174fbc6f9caaf1563896f2.jpg"
        Glide.with(this)
        .load(url)
        .into(binding.dImage)


        /////// 로그인 화면에서부터 userId 계속 들고있어야함
        // 신청버튼
        binding.btnSubmit.setOnClickListener {

            // 신청자 테이블에 userId, eventId, 수료여부 N 해서 보내기
            var data = EventAppData(0,userId,eventId,'N')

            // 확인 다이얼로그
            AlertDialog.Builder(this).run{
                setMessage("해당 행사에 참석하시겠습니까?")
                setPositiveButton("확인",object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        AlertDialog.Builder(this@EventDetailActivity).run {
                            setMessage("신청이 완료되었습니다.")
                            setNegativeButton("닫기",null)
                            show()
                        }
                        // db 연결버전
//                        Client.retrofit.insertAttend(data).enqueue(object:retrofit2.Callback<AttendData>{
//                            override fun onResponse(call: Call<AttendData>, response: Response<AttendData>) {
//                                AlertDialog.Builder(this@EventDetailActivity).run {
//                                    setMessage("신청이 완료되었습니다.")
//                                    setNegativeButton("닫기",null)
//                                }
//                            }
//
//                            override fun onFailure(call: Call<AttendData>, t: Throwable) {
//                                TODO("Not yet implemented")
//                            }
//                        }) // retrofit
                    }// onclick
                }) // positive
                setNegativeButton("취소",null)
                show()
            }
        }




        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }



    }// onCreate

}