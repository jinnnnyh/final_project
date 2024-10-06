package com.fullstack405.bitcfinalprojectkotlin.templete.event

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.EventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEventListBinding

class EventListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_event_list)
        val binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 데이터 생성
        var eventList = mutableListOf<EventData>()
        eventList.add(EventData(0,0,"제 4회 ai 컨퍼런스 안내","20241020","20241004",
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
                    "-게시일 기준으로 내림차순",'Y','Y',""))
        eventList.add(EventData(0,0,"제 3회 ai 컨퍼런스 안내","20241020","20241005","",'Y','Y',""))
        eventList.add(EventData(0,0,"제 2회 ai 컨퍼런스 안내","20241011","20241011","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241101","20240905","",'Y','N',""))

        var userId = intent.getLongExtra("userId",0)
        var userPermission = intent.getStringExtra("userPermission")

        // 어댑터 생성
        var eventListAdapter = EventListAdapter(eventList,userId,userPermission)
        binding.recyclerView.adapter = eventListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

//        // 승인 Y 인 이벤트만 불러오기
//        Client.event_retrofit.findEventList('Y').enqueue(object:retrofit2.Callback<List<EventData>>{
//            override fun onResponse(call: Call<List<EventData>>,response: Response<List<EventData>>) {
//                eventList = response.body() as MutableList<EventData>
//                eventListAdapter.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<List<EventData>>, t: Throwable) {
//                Log.d("event error","eventList load error")
//            }
//
//        })



        binding.btnBack.setOnClickListener {
            finish()
        }




    } // onCreate
}