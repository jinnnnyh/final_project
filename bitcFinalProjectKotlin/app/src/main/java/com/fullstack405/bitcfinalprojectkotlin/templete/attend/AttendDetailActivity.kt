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
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAttendDetailBinding

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
        var event = EventData(0,0,"제 4회 ai 컨퍼런스 안내","20241020","20241004",
            "메인화면\n" +
                    "- 관리자 = 예정된 행사/행사관리 \n" +
                    "- 회원 = 신청현황/신청내역\n" +
                    "- 나머지는 동일\n" +
                    "- 공지사항은 게시일 표출\n" +
                    "\n" +
                    "(관리자) 행사관리\n" +
                    "- 진행중/완료 탭으로 구분(전체보기 필요시 탭 추가 가능)\n" +
                    "- 현재 날짜 기준 오늘 포함 이전이면 진행중, 이후면 완료\n" +
                    "\n" +
                    "\n" +
                    "(공통)행사 안내\n" +
                    "- 게시일 기준 내림차순 \n" +
                    "- 제목에 모집중/마감 표시(정렬에는 영향X) 행사테이블에 마감 Y/N 컬럼 필요\n" +
                    "- 신청기간 보이게 하려면 해당컬럼 추가해야함 > 등록일 작업\n" +
                    "\n" +
                    "\n" +
                    "(공통)공지사항\n" +
                    "-게시일 기준으로 내림차순",'Y','Y',"")



//        lateinit var event: EventData
//        이벤트id로 해당 이벤트 정보만 불러오기
//        Client.event_retrofit.findEventId(eventId).enqueue(object:retrofit2.Callback<EventData>{
//            override fun onResponse(call: Call<EventData>, response: Response<EventData>) {
//                event = response.body()!!
//            }
//
//            override fun onFailure(call: Call<EventData>, t: Throwable) {
//                Log.d("eventDetail error","eventDetail load error")
//            }
//        }) // event_retrofit
        //        binding.dTitle.text = event.eventTitle
//        binding.dContent.text = event.eventContent
//        binding.dCreateDate.text=event.uploadDate

        binding.dTitle.text = event.eventTitle
        binding.dContent.text = event.eventContent
        binding.dCreateDate.text = event.eventDate

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


        // 항상 활성화
        // QR 확인
        binding.btnQR.setOnClickListener {
            // 큐알 페이지로 이동
            var intentQR = Intent(this,QrViewActivity::class.java)
            intentQR.putExtra("userId",userId)
            intentQR.putExtra("eventId",userId)
            // 1주일전 큐알코드 발행이니까 이벤트 일자 보내서 이미지 안 보이다가 1주일 전에 이미지 visible
            intentQR.putExtra("eventDate",event.eventDate)
            startActivity(intentQR)
        }


        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}