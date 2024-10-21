package com.fullstack405.bitcfinalprojectkotlin.templete.event

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.EventListAdapter
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEventListBinding
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class EventListActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityEventListBinding
    var userId = 0L
    var userPermission = "none"
    lateinit var eventList:MutableList<EventListData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userId = intent.getLongExtra("userId",0)
        userPermission = intent.getStringExtra("userPermission")!!

        // 데이터 생성
        eventList = mutableListOf<EventListData>()

        // 이벤트 리스트 데이터 초기 셋팅
        findEventList()
        
        binding.btnBack.setOnClickListener {
            finish()
        }

    } // onCreate

    // 액티비티 시작될때마다 새로 붙임
    override fun onResume() {
        super.onResume()
        findEventList()
    }
    
    private fun findEventList(){
        Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
            override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
                Log.d("event List load","${response.body()}")
                eventList.clear()

                val cal = Calendar.getInstance()
                cal.time = Date() // 오늘 날짜
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val today = dateFormat.format(cal.time)


                val resList = response.body() as MutableList<EventListData>
                // visibleDate <= 오늘
                for(item in resList){
                    if(item.visibleDate <= today ){
                        eventList.add(item)
                    }
                }
                var eventListAdapter = EventListAdapter(eventList,userId,userPermission)
                binding.recyclerView.adapter = eventListAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this@EventListActivity)

                eventListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
                Log.d("event error","eventList load error")
            }

        })
    }
}