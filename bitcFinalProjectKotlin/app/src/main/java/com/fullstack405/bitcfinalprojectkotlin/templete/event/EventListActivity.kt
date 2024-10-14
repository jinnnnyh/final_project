package com.fullstack405.bitcfinalprojectkotlin.templete.event

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

        var userId = intent.getLongExtra("userId",0)
        var userPermission = intent.getStringExtra("userPermission")

        // 데이터 생성
        var eventList = mutableListOf<EventListData>()

        // 이벤트 리스트 불러오기 성공하면 뷰에 어댑터 붙이는걸로,,,?
        Client.retrofit.findEventList().enqueue(object:retrofit2.Callback<List<EventListData>>{
            override fun onResponse(call: Call<List<EventListData>>, response: Response<List<EventListData>>) {
                Log.d("event List load","${response.body()}")
                eventList = response.body() as MutableList<EventListData>
                var eventListAdapter = EventListAdapter(eventList,userId,userPermission!!)
                binding.recyclerView.adapter = eventListAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this@EventListActivity)

                eventListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<EventListData>>, t: Throwable) {
                Log.d("event error","eventList load error")
            }

        })

        binding.btnBack.setOnClickListener {
            finish()
        }




    } // onCreate
}