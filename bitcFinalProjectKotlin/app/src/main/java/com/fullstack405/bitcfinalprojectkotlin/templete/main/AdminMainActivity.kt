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
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.AdminUpcomingEventData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAdminMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventDetailActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.notice_XXX.NoticeListActivity
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
        var userPermission = intent.getStringExtra("userPermission")

        if(userPermission.equals("ROLE_SECRETARY")){
            userPermission = "총무"
        }
        else if(userPermission.equals("ROLE_REGULAR")){
            userPermission ="정회원"
        }
        else{
            userPermission="협회장"
        }
        binding.userName.text ="${userPermission} ${userName}님"

        val intent_event = Intent(this, EventListActivity::class.java)
        intent_event.putExtra("userId",userId)
        intent_event.putExtra("userPermission",userPermission)

        val intentAttendList = Intent(this, AttendListActivity::class.java)
        intentAttendList.putExtra("userId",userId)

        val intent_userInfoEdit = Intent(this,EditUserInfoActivity::class.java)
        intent_userInfoEdit.putExtra("userId",userId)


        // 행사 관리 >> 행사안내 리스트로 이동
        binding.adminEventList.setOnClickListener {
            startActivity(intent_event)
        }

        // 신청 내역 리스트로 이동
        binding.attendList.setOnClickListener {
            startActivity(intentAttendList)
        }

        // 행사 내역 중 제일 빠른 일자 1개
        // 행사 당일 끝나는 시간 지나면 데이터 없음 > 화면에서 사라짐
        Client.retrofit.findAdminUpcomingEvent().enqueue(object:retrofit2.Callback<AdminUpcomingEventData>{
            override fun onResponse(call: Call<AdminUpcomingEventData>,response: Response<AdminUpcomingEventData>) {
                val data = response.body() as AdminUpcomingEventData
                val intent_eventDetail = Intent(this@AdminMainActivity,EventDetailActivity::class.java)
                binding.txtAttend.setOnClickListener {
                    intent_eventDetail.putExtra("eventId",data.eventId)
                    intent_eventDetail.putExtra("userId",userId)
                    intent_eventDetail.putExtra("userPermission",userPermission)
                    intent_eventDetail.putExtra("isRegistrationOpen",data.isRegistrationOpen)

                    startActivity(intent_eventDetail)
                }
                binding.txtAttend.text = data.eventTitle
                binding.attendDate.text = "행사일자 : ${data.eventDate} | ${data.startTime}"
            }

            override fun onFailure(call: Call<AdminUpcomingEventData>, t: Throwable) {
                binding.txtAttend.text = "예정된 행사가 없습니다."
            }

        })






        // 행사 안내 리스트로 이동
        binding.eventList.setOnClickListener {
            startActivity(intent_event)
        }

        // 행사 안내
        val eventList = mutableListOf<EventListData>()
        lateinit var mainEventListAdapter:MainEventListAdapter

        // db 연결버전
        Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
            override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
                var resList = response.body() as MutableList<EventListData>

                // 목록은 항상 내림차순으로 받아옴, 상위 5개만 메인에 표출
                if(resList.size-1 < 5){ // 5개 미만일 경우
                    for(i in 0..resList.size-1){
                        eventList.add(resList[i])
                    }
                }else{
                    for(i in 0..5){
                        eventList.add(resList[i])
                    }
                }

                mainEventListAdapter = MainEventListAdapter(eventList,userId,userPermission!!)

                binding.eventRecyclerView.adapter = mainEventListAdapter
                binding.eventRecyclerView.layoutManager = LinearLayoutManager(this@AdminMainActivity)

                mainEventListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
                Log.d("main eventlsit error", "main eventList load error")
            }
        })

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