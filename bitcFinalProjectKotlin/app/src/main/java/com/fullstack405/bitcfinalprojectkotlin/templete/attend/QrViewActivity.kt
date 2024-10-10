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
import com.fullstack405.bitcfinalprojectkotlin.data.QRdata
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrViewBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import retrofit2.Call
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Date


///////////// 사용 xxxxxxxxxxxxx
class QrViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_qr_view)
        val binding = ActivityQrViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var userId = intent.getLongExtra("userId",0)
        var eventId = intent.getLongExtra("evnetId",0)


//        // 큐알코드 생성
//        var content = "${eventId}-${scheduleId}-${userId}"
//        val barcodeEncoder = BarcodeEncoder()
//        val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 512, 512)

        // 스케쥴id(오름차순), QR 이미지 주소, 행사날짜
        // QR 이미지 디비에서 받아와서 화면에 출력하기 > glide
        // 행사 일자 중 첫번째 행사 큐알을 1주일 전에 표시 ~ 첫번째 날 까지
        // 이후로는 하루마다 비교해서 보이고, 마지막날 다음날부터 안보이게 하기

        lateinit var QRlist:MutableList<QRdata>
        Client.retrofit.findQRImageList(eventId, userId).enqueue(object:retrofit2.Callback<List<QRdata>>{
            override fun onResponse(call: Call<List<QRdata>>, response: Response<List<QRdata>>) {
                QRlist = response.body() as MutableList<QRdata>
            }

            override fun onFailure(call: Call<List<QRdata>>, t: Throwable) {
                Log.d("QR list error","${t.message}")
            }

        })


        // 캘린더 사용
        // 각 변수를 저정할 인스턴스
        val cal_t = Calendar.getInstance() // 오늘
        val cal_s = Calendar.getInstance() // 시작-7
        val cal_sdate = Calendar.getInstance() // 시작일
        val cal_e = Calendar.getInstance() // 끝

        val dateFormat =SimpleDateFormat("yyyyMMdd") // 포맷설정

        // 오늘날짜
        cal_t.time = Date()
        val td = dateFormat.format(cal_t.time) // 오늘 날짜 string 타입


        var sd = QRlist[0].eventDate // 제일 처음 회차의 날짜
        var ed = QRlist[QRlist.size-1].eventDate // 마지막 회차의 날짜

        val start_7:Date = dateFormat.parse(sd) // 이벤트 날짜 String > Date 변환
        val startDate:Date = dateFormat.parse(sd)
        val endDate:Date = dateFormat.parse(ed)

        cal_s.time = start_7 // 시작일 - 7일
        cal_sdate.time = startDate // 시작일
        cal_e.time = endDate // 종료일

        cal_s.add(Calendar.DATE,-7) // 시작일 일주일 전 날짜

        var url="http://10.100.105.205:8080/eventImg/"


        // 첫번째 일자는 항상 인덱스 0 이고 얘는 1주일전부터 보여야함
        if(cal_t in cal_s..cal_sdate){
            Glide.with(this@QrViewActivity)
                .load(url+QRlist[0].imgQrName)
                .into(binding.imgQr)
        }
        // 시작일 보다 오늘 날짜가 크면
        else if(cal_sdate < cal_t){
            // 오늘 날짜랑 같은 이미지만 뿌리기
            for(i in 0..QRlist.size-1){
                if(td == QRlist[i].eventDate){
                    Glide.with(this@QrViewActivity)
                        .load(url+QRlist[i].imgQrName)
                        .into(binding.imgQr)
                    break;
                }
            }//for
        }// else if




        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}