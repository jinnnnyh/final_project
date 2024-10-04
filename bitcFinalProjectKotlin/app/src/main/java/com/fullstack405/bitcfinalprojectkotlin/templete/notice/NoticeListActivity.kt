package com.fullstack405.bitcfinalprojectkotlin.templete.notice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainNoticeListAdapter
import com.fullstack405.bitcfinalprojectkotlin.adapter.NoticeListAdapter
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityNoticeListBinding

class NoticeListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_notice_list)
        val binding = ActivityNoticeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 공지사항 어댑터
        var noticeList = mutableListOf<NoticeData>()
        noticeList.add(NoticeData(0,"운영위원 모집 공고1",
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
        noticeList.add(NoticeData(0,"운영위원 모집 공고2",
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
        noticeList.add(NoticeData(0,"운영위원 모집 공고3","aaaaaaaaa","20240203"))
        noticeList.add(NoticeData(0,"운영위원 모집 공고4","aaaaaaaaaaaa","20240203"))

        var noticeListAdapter = NoticeListAdapter(noticeList)
        binding.recyclerView.adapter = noticeListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.btnBack.setOnClickListener {
            finish()
        }

    } // onCreate
}