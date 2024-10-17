package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.Intent
import android.media.metrics.Event
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.MainEventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.UserUpcomingEventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityMainBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendDetailActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.event.EventListActivity
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  lateinit var binding:ActivityMainBinding

  lateinit var eventList:MutableList<EventListData>
  lateinit var mainEventListAdapter:MainEventListAdapter
  
  var userId = 0L
  var userPermission = "none"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    userId = intent.getLongExtra("userId",0)
    var userName = intent.getStringExtra("userName")
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

    binding.userName.text = "${userPermission} ${userName}님"
    var intent_event = Intent(this, EventListActivity::class.java)
    intent_event.putExtra("userId",userId)
    intent_event.putExtra("userPermission",userPermission)


    var intent_userInfoEdit = Intent(this,EditUserInfoActivity::class.java)
    intent_userInfoEdit.putExtra("userId",userId)


    var intentAttendList = Intent(this,AttendListActivity::class.java)
    intentAttendList.putExtra("userId",userId)
    intentAttendList.putExtra("userName",userName)




    // 신청 현황
    // 신청 내역 버튼 연결
    binding.attendList.setOnClickListener {
      startActivity(intentAttendList)
    }

    // 신청 내역 중 제일 빠른 일자 1개 초반 셋팅
    updateUpcomingEvent()

    // 행사 안내 목록으로 이동
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
      var intentLogin = Intent(this,LoginActivity::class.java)
      startActivity(intentLogin)
    }


  }// oncreate


  // 액티비티 다시 호출될 때
  override fun onResume() {
    super.onResume()
    updateUpcomingEvent() // 예정 1개
    findEventList() // 행사 목록
  }

  // resultcode를 가지고 왔을 때
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(resultCode == RESULT_OK){
      updateUpcomingEvent()
    }
  } // onActivityResult

  private fun updateUpcomingEvent(){
    Client.retrofit.findUpcomingEventForUser(userId).enqueue(object:retrofit2.Callback<UserUpcomingEventData>{
      override fun onResponse(call: Call<UserUpcomingEventData>,response: Response<UserUpcomingEventData>) {
        val data = response.body() as UserUpcomingEventData?
        if(response.body() == null){
          binding.txtAttend.text = "예정된 행사가 없습니다."
          binding.attendDate.isVisible = false
        }else{
          val intent_attendDetail = Intent(this@MainActivity, AttendDetailActivity::class.java)
          binding.txtAttend.setOnClickListener {
            // 회원) 신청 상세 페이지로 이동
            intent_attendDetail.putExtra("eventId",data!!.eventId)
            intent_attendDetail.putExtra("userId",userId)
            intent_attendDetail.putExtra("complete",data.eventComp)
            startActivity(intent_attendDetail)
          }
          binding.attendDate.isVisible = true
          binding.txtAttend.text = data!!.eventTitle
          binding.attendDate.text = "행사일 : ${data.eventDate}  |  ${data.startTime}"

        }
      }
      override fun onFailure(call: Call<UserUpcomingEventData>, t: Throwable) {
        Log.d("findUpcomingEventForUser","error :${t.message}")
      }
    })
  } // updateUpcomingEvent

  
  private fun findEventList(){
    Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
      override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
        eventList.clear()

        var resList = response.body() as MutableList<EventListData>?

        // 목록은 항상 내림차순으로 받아옴, 상위 5개만 메인에 표출
        if(resList!!.size-1 < 5){ // 5개 미만일 경우
          for(i in 0..resList.size-1){
            eventList.add(resList[i])
          }
        }else{
          for(i in 0..5){
            eventList.add(resList[i])
          }
        }

        mainEventListAdapter = MainEventListAdapter(eventList,userId,userPermission)

        binding.eventRecyclerView.adapter = mainEventListAdapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        mainEventListAdapter.notifyDataSetChanged()
      }

      override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
        Log.d("main eventlsit error", "main eventList load error")
      }
    })
  }

}// main


