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
        eventList.add(
            EventData(0,0,"제 4회 ai 컨퍼런스 안내",
                "controller\n" +
                        "@PostMapping(value = \"/attend/{eventId}/{userId}\")\n" +
                        "    public ResponseEntity<AttendInfoEntity> insertAttendIfo(AttendInfoEntity attendInfoEntity) {\n" +
                        "\n" +
                        "        // 참석 정보 저장\n" +
                        "        AttendInfoEntity savedAttend = attendInfoService.save(attendInfoEntity);\n" +
                        "\n" +
                        "        // 회원이 가져온 큐알에 있는 행사 id랑 관리자가 qr 찍을 때 가진 행사 id랑 같은지 다른지 확인\n" +
                        "\n" +
                        "        return ResponseEntity.ok().body(savedAttend);\n" +
                        "    }\n",
                "","",'N'))
        eventList.add(EventData(0,0,"제 3회 ai 컨퍼런스 안내","이벤트내용부분","20241005","",'N'))
        eventList.add(EventData(1,0,"제 2회 ai 컨퍼런스 안내","이벤트내용부분","20241011","",'N'))
        eventList.add(EventData(2,0,"제 1회 ai 컨퍼런스 안내","이벤트내용부분","20240905","",'N'))
        eventList.add(EventData(3,0,"제 1회 ai 컨퍼런스 안내","이벤트내용부분","20240905","",'N'))
        eventList.add(EventData(4,0,"제 1회 ai 컨퍼런스 안내","이벤트내용부분","20240905","",'N'))
        eventList.add(EventData(5,0,"제 1회 ai 컨퍼런스 안내","이벤트내용부분","20240905","",'N'))
        eventList.add(EventData(6,0,"제 1회 ai 컨퍼런스 안내","이벤트내용부분","20240905","",'N'))


        var userId = intent.getLongExtra("userId",0)
        var userPermission = intent.getStringExtra("userPermission")

        // 어댑터 생성
        var eventListAdapter = EventListAdapter(eventList,userId,userPermission!!)
        binding.recyclerView.adapter = eventListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

//        // 승인 Y 인 이벤트만 불러오기
//        Client.retrofit.findEventList('Y').enqueue(object:retrofit2.Callback<List<EventData>>{
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