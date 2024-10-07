package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.content.Intent
import android.media.metrics.Event
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.data.EventScheduleData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAttendDetailBinding
import retrofit2.Call
import retrofit2.Response
import java.lang.Integer.parseInt
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


        // 데이터 생성
        var event = EventData(0 ,0,"제 3회 ai 컨퍼런스 안내","이벤트내용부분","20241005","",'N')
        var scheduleList = mutableListOf<EventScheduleData>()
        scheduleList.add(EventScheduleData(3,0,"09:00","12:00","20241007"))
        scheduleList.add(EventScheduleData(1,0,"09:00","12:00","20241008"))
        scheduleList.add(EventScheduleData(2,0,"09:00","12:00","20241009"))




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
//        binding.dCreateDate.text=event.uploadDate

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

        binding.dTitle.text = event.eventTitle
        binding.dContent.text = event.eventContent
        binding.dCreateDate.text = event.visibleDate // 게시일

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


//        // 이벤트Id로 스케쥴 리스트 불러오기
//        lateinit var scheduleList: MutableList<EventScheduleData>
//        Client.retrofit.findScheduleList(eventId).enqueue(object:retrofit2.Callback<List<EventScheduleData>>{
//            override fun onResponse(
//                call: Call<List<EventScheduleData>>,response: Response<List<EventScheduleData>>) {
//                scheduleList = response.body() as MutableList<EventScheduleData>
//
//            }
//
//            override fun onFailure(call: Call<List<EventScheduleData>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })

        // 오늘 날짜 계산
        var sysDate = Date(System.currentTimeMillis())
        var dateFormat = SimpleDateFormat("yyyyMMdd")
        var today = parseInt(dateFormat.format(sysDate))

        var scheduleId = 0L

        for(item in scheduleList){
            // 오늘 날짜랑 스케쥴상 일자가 같으면 스케줄id 저장하고 멈춤
            if(item.eventDate == today.toString()){
                scheduleId = item.scheduleId
                break;
            }
        }


        var startDate = scheduleList.get(0).eventDate // 제일 처음 회차의 날짜
        var endDate = scheduleList.get(scheduleList.size-1).eventDate // 마지막 회차의 날짜

        binding.btnQR.isEnabled = false // 기본 비활성화

        // 행사 1주일 전  ~ 마지막날 까지 활성화 마지막 다음날 비활성화
        if(parseInt(startDate)-7 <= today  &&  today <= parseInt(endDate)){
            binding.btnQR.isEnabled = true
            // QR 확인
            binding.btnQR.setOnClickListener {
                // 큐알 페이지로 이동
                var intentQR = Intent(this,QrViewActivity::class.java)
                intentQR.putExtra("userId",userId)
                intentQR.putExtra("eventId",eventId)
                intentQR.putExtra("scheduleId",scheduleId)
                startActivity(intentQR)
            }
        }



        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}