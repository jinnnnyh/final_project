package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.Intent
import android.media.metrics.Event
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.EventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainEventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainNoticeListAdapter
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.admin.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.notice.NoticeListActivity
import retrofit2.Call
import retrofit2.Response
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
//    setContentView(R.layout.activity_main)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    var userId = intent.getLongExtra("userId",0)
    var userName = intent.getStringExtra("userName")

    binding.userName.text = "회원 ${userName}님"
    var intent_event = Intent(this,EventListActivity::class.java)
    intent_event.putExtra("userId",userId)

    var intent_attend = Intent(this,AttendListActivity::class.java)
    intent_attend.putExtra("userId",userId)

    var intent_notice = Intent(this,NoticeListActivity::class.java)


    // 행사 안내 어댑터
    var eventList = mutableListOf<EventData>()
    eventList.add(EventData(0,0,"제 4회 ai 컨퍼런스 안내","2024.10.20","20241004",
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

    var mainEventListAdapter = MainEventListAdapter(testList,userId)
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
    noticeList.add(NoticeData(0,"운영위원 모집 공고",
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
              "-게시일 기준으로 내림차순","20240203"))
    noticeList.add(NoticeData(0,"운영위원 모집 공고",
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
              "-게시일 기준으로 내림차순","20240203"))
    noticeList.add(NoticeData(0,"운영위원 모집 공고","","20240203"))
    noticeList.add(NoticeData(0,"운영위원 모집 공고","","20240203"))

    var mainNoticeListAdapter = MainNoticeListAdapter(noticeList)
    binding.noticeRecyclerView.adapter = mainNoticeListAdapter
    binding.noticeRecyclerView.layoutManager = LinearLayoutManager(this)

    binding.noticeList.setOnClickListener {
      startActivity(intent_notice)
    }

  }// oncreate
}// main


