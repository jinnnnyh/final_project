package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainEventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainNoticeListAdapter
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAdminMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.notice.NoticeListActivity

class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_admin_main)
        val binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userId = intent.getLongExtra("userId",0)
        val userName = intent.getStringExtra("userName")
        var userPermission = intent.getStringExtra("userPermission")

        binding.userName.text ="${userPermission} ${userName}님"

        val intent_event = Intent(this, EventListActivity::class.java)
        intent_event.putExtra("userId",userId)
        intent_event.putExtra("userPermission",userPermission)


        val intent_notice = Intent(this, NoticeListActivity::class.java)
        val intent_userInfoEdit = Intent(this,EditUserInfoActivity::class.java)

        // 행사 안내 어댑터
        val eventList = mutableListOf<EventData>()
        eventList.add(
            EventData(0,0,"제 4회 ai 컨퍼런스 안내","20241020","20241004",
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
        )
        eventList.add(EventData(0,0,"제 3회 ai 컨퍼런스 안내","20241020","20241005","",'Y','Y',""))
        eventList.add(EventData(0,0,"제 2회 ai 컨퍼런스 안내","20241020","20241011","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241020","20240905","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241020","20240905","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241020","20240905","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241020","20240905","",'Y','N',""))
        eventList.add(EventData(0,0,"제 1회 ai 컨퍼런스 안내","20241020","20240905","",'Y','N',""))

        var testList = mutableListOf<EventData>()
        for(i in 0..3){
            testList.add(eventList[i])
        }

        var mainEventListAdapter = MainEventListAdapter(testList,userId, userPermission!!)
        binding.eventRecyclerView.adapter = mainEventListAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.eventList.setOnClickListener {
            startActivity(intent_event)
        }

        // db 연결버전
//    Client.event_retrofit.findEventList('Y').enqueue(object:retrofit2.Callback<List<EventData>>{
//      override fun onResponse(call: Call<List<EventData>>, response: Response<List<EventData>>) {
//        var resList = response.body() as MutableList<EventData>
//        // 목록은 항상 내림차순으로 받아옴, 상위 4개만 메인에 표출
//        for(i in 0..3){
//          eventList.add(resList[i])
//        }
//        eventListAdapter.notifyDataSetChanged()
//      }
//
//      override fun onFailure(call: Call<List<EventData>>, t: Throwable) {
//        Log.d("main eventlsit error", "main eventList load error")
//      }
//    })

        // 공지사항 어댑터
        var noticeList = mutableListOf<NoticeData>()
        noticeList.add(
            NoticeData(0,"운영위원 모집 공고",
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
                    "-게시일 기준으로 내림차순","20240203")
        )
        noticeList.add(
            NoticeData(0,"운영위원 모집 공고",
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
                    "-게시일 기준으로 내림차순","20240203")
        )
        noticeList.add(NoticeData(0,"운영위원 모집 공고","","20240203"))
        noticeList.add(NoticeData(0,"운영위원 모집 공고","","20240203"))

        var mainNoticeListAdapter = MainNoticeListAdapter(noticeList)
        binding.noticeRecyclerView.adapter = mainNoticeListAdapter
        binding.noticeRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.noticeList.setOnClickListener {
            startActivity(intent_notice)
        }

        // 회원정보수정 클릭 이벤트
        binding.userInfoEdit.setOnClickListener {
            intent.putExtra("userId",userId)
            startActivity(intent_userInfoEdit)
        }

        // 로그아웃
        binding.logout.setOnClickListener {
            var intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }


    }//onCreate
}