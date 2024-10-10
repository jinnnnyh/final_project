package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainEventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainNoticeListAdapter
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAdminMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.notice.NoticeListActivity
import retrofit2.Call
import retrofit2.Response

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
        val userPermission = intent.getStringExtra("userPermission")

        binding.userName.text ="${userPermission} ${userName}님"

        val intent_event = Intent(this, EventListActivity::class.java)
        intent_event.putExtra("userId",userId)
        intent_event.putExtra("userPermission",userPermission)

        val intentAttendList = Intent(this, AttendListActivity::class.java)
        intentAttendList.putExtra("userId",userId)

        val intent_notice = Intent(this, NoticeListActivity::class.java)
        val intent_userInfoEdit = Intent(this,EditUserInfoActivity::class.java)

        // 행사 안내 어댑터
        val eventList = mutableListOf<EventListData>()


        val mainEventListAdapter = MainEventListAdapter(eventList,userId, userPermission!!)
        binding.eventRecyclerView.adapter = mainEventListAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(this)

        // 행사 안내
        binding.eventList.setOnClickListener {
            startActivity(intent_event)
        }

        // 신청 내역
        binding.attendList.setOnClickListener {
            startActivity(intentAttendList)
        }


        // db 연결버전
    Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
      override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
        var resList = response.body() as MutableList<EventListData>
        // 목록은 항상 내림차순으로 받아옴, 상위 4개만 메인에 표출
        for(i in 0..resList.size-1){
          eventList.add(resList[i])
        }
          mainEventListAdapter.notifyDataSetChanged()
      }

      override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
        Log.d("main eventlsit error", "main eventList load error")
      }
    })

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