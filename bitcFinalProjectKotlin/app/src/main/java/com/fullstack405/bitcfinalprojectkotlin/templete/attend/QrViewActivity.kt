package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrViewBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import retrofit2.Call
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Date

class QrViewActivity : AppCompatActivity() {

    var userId = 0L
    var eventId = 0L
    lateinit var QRlist:List<Map<String,Any>>
    lateinit var binding: ActivityQrViewBinding

    // 캘린더 사용
    // 각 변수를 저정할 인스턴스
    val cal_t = Calendar.getInstance() // 오늘
    val cal_s = Calendar.getInstance() // 시작-7
    val cal_sdate = Calendar.getInstance() // 시작일
    val cal_e = Calendar.getInstance() // 끝
    var td = "null"

    var url="http://10.100.105.168:8080/qrImg/"

    val dateFormat =SimpleDateFormat("yyyy-MM-dd") // 포맷설정
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQrViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userId = intent.getLongExtra("userId",0)
        eventId = intent.getLongExtra("eventId",0)
        var eventName = intent.getStringExtra("eventName")

        binding.eventName.text =eventName


        // 오늘날짜
        cal_t.time = Date()
        td = dateFormat.format(cal_t.time) // 오늘 날짜 string 타입


        binding.imgQr.isVisible = false

        // 초기 데이터 셋팅
        findQRImageList()

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    } // onCreate
    // 액티비티 시작될때마다 새로 붙임
    override fun onResume() {
        super.onResume()
        findQRImageList()
    }

    private fun findQRImageList(){
        Client.retrofit.findQRImageList(eventId, userId).enqueue(object:retrofit2.Callback<List<Map<String,Any>>>{
            override fun onResponse(call: Call<List<Map<String,Any>>>, response: Response<List<Map<String,Any>>>) {
//                [{qrImage=39005063789000.png, scheduleId=3.0, eventDate=2024-10-10}, {qrImage=39005239314600.png, scheduleId=4.0, eventDate=2024-10-11}]
                QRlist = response.body()!!
                Log.d("QR list response","${QRlist[0].get("eventDate").toString()}//${QRlist[0].get("qrImage").toString()}")

                var sd = QRlist[0].get("eventDate").toString() // 제일 처음 회차의 날짜
//                var ed = QRlist[QRlist.size-1].get("eventDate").toString() // 마지막 회차의 날짜

//                val start_7: Date? = dateFormat.parse(sd) // 이벤트 날짜 String > Date 변환
                val startDate: Date? = dateFormat.parse(sd)
//                val endDate: Date? = dateFormat.parse(ed)

                cal_s.time = startDate // 시작일 - 7일
                cal_sdate.time = startDate // 시작일
//                cal_e.time = endDate // 종료일

                cal_s.add(Calendar.DATE,-7) // 시작일 일주일 전 날짜


                // 첫번째 일자는 항상 인덱스 0 이고 얘는 1주일전부터 보여야함
                if (cal_s <= cal_t) {
                    binding.imgQr.isVisible = true
                    if (cal_t <= cal_sdate) {
                        binding.eventDate.text = QRlist[0].get("eventDate").toString()
                        Glide.with(this@QrViewActivity)
                            .load(url + QRlist[0].get("qrImage"))
                            .into(binding.imgQr)
                    }
                    // 시작 날짜보다 오늘 날짜가 더 크면 행사일자 = 오늘인 큐알 보임
                    else if (cal_t > cal_sdate) {
                        for (i in 0..QRlist.size - 1) {
                            if (td == QRlist[i].get("eventDate").toString()) {
                                binding.eventDate.text = "${QRlist[i].get("eventDate").toString()}"
                                Glide.with(this@QrViewActivity)
                                    .load(url + QRlist[i].get("qrImage"))
                                    .into(binding.imgQr)
                                break;
                            }
                            // 오늘 날짜랑 일치하는게 없을 시 invisible, 행사 마지막 날짜
                            binding.eventDate.text = QRlist[QRlist.size-1].get("eventDate").toString()
                            binding.imgQr.isVisible = false
                        }//for
                    }
                }
            } // onResponse

            override fun onFailure(call: Call<List<Map<String,Any>>>, t: Throwable) {
                Log.d("QR list error","${t.message}")
            }

        })
    }
}