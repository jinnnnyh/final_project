package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainEventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.AdminUpcomingEventData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.UserUpcomingEventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAdminMainBinding
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendDetailActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventDetailActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class AdminMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminMainBinding
    lateinit var eventList:MutableList<EventListData>
    lateinit var mainEventListAdapter:MainEventListAdapter
    var userId = 0L
    var userPermission =""
    var userName = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        subToTopic("notice") // 알람 구독

        userId = intent.getLongExtra("userId",0)
        userName = intent.getStringExtra("userName")!!
        userPermission = intent.getStringExtra("userPermission")!!

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

        // 행사 안내 목록
        val intent_event = Intent(this, EventListActivity::class.java)
        intent_event.putExtra("userId",userId)
        intent_event.putExtra("userPermission",userPermission)

        // 신청 내역 목록
        val intentAttendList = Intent(this, AttendListActivity::class.java)
        intentAttendList.putExtra("userId",userId)
        intentAttendList.putExtra("userName",userName)

        // 회원정보수정
        val intent_userInfoEdit = Intent(this,EditUserInfoActivity::class.java)
        intent_userInfoEdit.putExtra("userId",userId)


        // 행사 관리 >> 행사안내 리스트로 이동
        binding.adminEventList.setOnClickListener {
            startActivity(intent_event)
        }
        // 예정된 제일 빠른 행사 1개
        updateAdminUpcomingEvent()


        // 신청 내역 리스트로 이동
        binding.attendList2.setOnClickListener {
            startActivity(intentAttendList)
        }


        // 신청 내역 중 제일 빠른 행사 1개
        updateUpcomingEvent()


        // 행사 안내 리스트로 이동
        binding.eventList.setOnClickListener {
            startActivity(intent_event)
        }

        // 행사 안내
        eventList = mutableListOf<EventListData>()
        // 행사 목록 데이터 초기 셋팅
        findEventList()

        // 회원정보수정 클릭 이벤트
        binding.userInfoEdit.setOnClickListener {
            intent.putExtra("userId",userId)
            startActivity(intent_userInfoEdit)
        }

        // 로그아웃
        binding.logout.setOnClickListener {
            logoutUser()
        }
    }//onCreate

    // 액티비티 다시 호출될 때
    override fun onResume() {
        super.onResume()
        updateAdminUpcomingEvent() // 행사 예정ㅁ 1개
        updateUpcomingEvent() // 신청현황 1개
        findEventList() // 행사 목록
    }

    // resultcode를 가지고 왔을 때
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            updateAdminUpcomingEvent()
        }
    } // onActivityResult

    private fun updateAdminUpcomingEvent(){
        // 행사 내역 중 제일 빠른 일자 1개
        // 행사 당일 끝나는 시간 지나면 데이터 없음 > 화면에서 사라짐
        // onFailure로 가는게 아니라 onResponse로 null 값이 넘어옴
        Client.retrofit.findAdminUpcomingEvent().enqueue(object:retrofit2.Callback<AdminUpcomingEventData>{
            override fun onResponse(call: Call<AdminUpcomingEventData>,response: Response<AdminUpcomingEventData>) {
                val data = response.body() as AdminUpcomingEventData?
                if(response.body() == null){
                    binding.txtAttend.text = "예정된 행사가 없습니다."
                    binding.attendDate.isVisible = false
                }
                else{
                    val intent_eventDetail = Intent(this@AdminMainActivity,EventDetailActivity::class.java)
                    binding.txtAttend.setOnClickListener {
                        // 관리자) 행사 상세 페이지로 이동
                        intent_eventDetail.putExtra("eventId",data!!.eventId)
                        intent_eventDetail.putExtra("userId",userId)
                        intent_eventDetail.putExtra("userPermission",userPermission)
                        intent_eventDetail.putExtra("isRegistrationOpen",data.isRegistrationOpen)

                        startActivity(intent_eventDetail)
                    }
                    binding.attendDate.isVisible = true
                    binding.txtAttend.text = data!!.eventTitle
                    binding.attendDate.text = "행사일자 : ${data.eventDate} | ${data.startTime}"
                }

            }

            override fun onFailure(call: Call<AdminUpcomingEventData>, t: Throwable) {
                Log.d("findAdminUpcomingEvent","error :${t.message}")
                binding.txtAttend.text = "예정된 행사가 없습니다."
                binding.attendDate.isVisible = false
            }

        })
    } // updateAdminUpcomingEvent

    private fun updateUpcomingEvent(){
        Client.retrofit.findUpcomingEventForUser(userId).enqueue(object:retrofit2.Callback<UserUpcomingEventData>{
            override fun onResponse(call: Call<UserUpcomingEventData>,response: Response<UserUpcomingEventData>) {
                val data = response.body() as UserUpcomingEventData?
                if(response.body() == null){
                    binding.txtAttend2.text = "예정된 행사가 없습니다."
                    binding.attendDate2.isVisible = false
                }else{
                    // 신청 상세
                    val intent_attendDetail = Intent(this@AdminMainActivity, AttendDetailActivity::class.java)
                    binding.txtAttend2.setOnClickListener {
                        // 회원) 신청 상세 페이지로 이동
                        intent_attendDetail.putExtra("eventId",data!!.eventId)
                        intent_attendDetail.putExtra("userId",userId)
                        intent_attendDetail.putExtra("userName",userName)
                        startActivity(intent_attendDetail)
                    }
                    binding.attendDate2.isVisible = true
                    binding.txtAttend2.text = data!!.eventTitle
                    binding.attendDate2.text = "행사일 : ${data.eventDate}  |  ${data.startTime}"

                }
            }
            override fun onFailure(call: Call<UserUpcomingEventData>, t: Throwable) {
                Log.d("findUpcomingEventForUser","error :${t.message}")
                binding.txtAttend2.text = "예정된 행사가 없습니다."
                binding.attendDate2.isVisible = false
            }
        })
    } // updateUpcomingEvent

    private fun findEventList(){
        Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
            override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
                eventList.clear()

                val resList = response.body() as MutableList<EventListData>? // 전체 리스트 저장
                val resList2 = mutableListOf<EventListData>() // visible 행사만 저장할 데이터 초기화

                val cal = Calendar.getInstance()
                cal.time = Date() // 오늘 날짜
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val today = dateFormat.format(cal.time)

                if (resList != null) {
                    for(item in resList){
                        if(item.visibleDate <= today){
                            resList2.add(item) // visible 행사만 저장
                        }
                    }
                }
                // 목록은 항상 내림차순으로 받아옴, 상위 3개만 메인에 표출
                if(resList2!!.size-1 < 3){ // 3개 미만일 경우
                    for(i in 0..resList2.size-1){
                        eventList.add(resList2[i])
                    }
                }else{
                    for(i in 0..2){
                        eventList.add(resList2[i])
                    }
                }

                mainEventListAdapter = MainEventListAdapter(eventList,userId,userPermission)

                binding.eventRecyclerView.adapter = mainEventListAdapter
                binding.eventRecyclerView.layoutManager = LinearLayoutManager(this@AdminMainActivity)

                mainEventListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
                Log.d("main eventlsit error", "main eventList load error")
            }
        })
    }//findEventList
    // 알람 구독
    private fun subToTopic(topic:String){
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to topic"
                if (!task.isSuccessful) {
                    msg = "Subscription failed"
                }
                Log.d("FCM", msg)
            }
    }

    private fun logoutUser(){
        val sharedPreferences = getSharedPreferences("app_pref", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("userId") // 사용자 ID 삭제
            remove("userRole")
            remove("userName")
            apply()
        }
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

} //main