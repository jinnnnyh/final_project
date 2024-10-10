package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.EventScheduleData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAttendDetailBinding
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class AttendDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_attend_detail)
        val binding = ActivityAttendDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var userId = intent.getLongExtra("userId",0)
        var eventId = intent.getLongExtra("eventId",0)
        var complete = intent.getCharExtra("complete",'N')



        lateinit var event: EventDetailData
//        이벤트id로 해당 이벤트 정보만 불러오기
        Client.retrofit.findEventId(eventId).enqueue(object:retrofit2.Callback<EventDetailData>{
            override fun onResponse(call: Call<EventDetailData>, response: Response<EventDetailData>) {
                event = response.body() as EventDetailData
                binding.dTitle.text = event.eventTitle
                binding.dContent.text = event.eventContent
                binding.dCreateDate.text=event.visibleDate
                binding.dWriter.text = event.posterUserName
            }

            override fun onFailure(call: Call<EventDetailData>, t: Throwable) {
                Log.d("eventDetail error","eventDetail load error")
            }
        }) // retrofit

        // Y 이면 활성화 N이면 비활성화
        // 참석증 확인
        if(complete == 'Y'){
            binding.btnComplete.isEnabled = true
            binding.btnComplete.setOnClickListener {
                // 참석증 페이지로 이동
                var intentComplete =Intent(this,CompleteViewActivity::class.java)
                intentComplete.putExtra("userId",userId)
                intentComplete.putExtra("eventId",userId)
                startActivity(intentComplete)
            }
        }
        else{
            binding.btnComplete.isEnabled = false
        }


        // QR 확인
        binding.btnQR.setOnClickListener {
            // 큐알 페이지로 이동
            var intentQR = Intent(this, QrViewActivity::class.java)
            intentQR.putExtra("userId", userId)
            intentQR.putExtra("eventId", eventId)
            startActivity(intentQR)
        }
//        } // if


        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }//onCreate


}