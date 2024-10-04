package com.fullstack405.bitcfinalprojectkotlin.templete.event.admin

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.AttendData
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEventDetailBinding
import retrofit2.Call
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_event_detail)
        val binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var eventId = intent.getLongExtra("eventId",0)
        var title = intent.getStringExtra("eventTitle")
        var content = intent.getStringExtra("eventContent")
        var date = intent.getStringExtra("eventDate")
        var uploadDate = intent.getStringExtra("uploadDate")
        var userId = intent.getLongExtra("userId",0)





//        lateinit var event: EventData
//        이벤트id로 해당 이벤트 정보만 불러오기
//        Client.event_retrofit.findEventId(eventId).enqueue(object:retrofit2.Callback<EventData>{
//            override fun onResponse(call: Call<EventData>, response: Response<EventData>) {
//                event = response.body()!!
//            }
//
//            override fun onFailure(call: Call<EventData>, t: Throwable) {
//                Log.d("eventDetail error","eventDetail load error")
//            }
//        }) // event_retrofit

//        binding.dTitle.text = event.eventTitle
//        binding.dContent.text = event.eventContent
//        binding.dCreateDate.text=event.uploadDate

        binding.dTitle.text = title
        binding.dContent.text = content
        binding.dCreateDate.text = uploadDate

//        var url = "http://10.100.105.194:8811/events/"
//        var posterName = event.eventPoster

        var url = "https://godomall.speedycdn.net/246ac6860cab30de5414f7d17e2bb4bc/editor/board/event/453/730baf1671174fbc6f9caaf1563896f2.jpg"
        Glide.with(this)
        .load(url)
        .into(binding.dImage)


        /////// 로그인 화면에서부터 userId 계속 들고있어야함
        // 신청버튼
        binding.btnSubmit.setOnClickListener {
            // attend data에 회원id, 행사id, 참석여부 'N' 넣어서 추가
            // attend_info 테이블의 참석여부는 당일 행사 참석해서 QR 스캔 Y/N
            var data = AttendData(0,eventId,'N')

            // 확인 다이얼로그
            AlertDialog.Builder(this).run{
                setMessage("해당 행사에 참석하시겠습니까?")
                setPositiveButton("확인",object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        AlertDialog.Builder(this@EventDetailActivity).run {
                            setMessage("신청이 완료되었습니다.")
                            setNegativeButton("닫기",null)
                            show()
                        }
                        // db 연결버전
//                        Client.attend_retrofit.insertAttend(data).enqueue(object:retrofit2.Callback<AttendData>{
//                            override fun onResponse(call: Call<AttendData>, response: Response<AttendData>) {
//                                AlertDialog.Builder(this@EventDetailActivity).run {
//                                    setMessage("신청이 완료되었습니다.")
//                                    setNegativeButton("닫기",null)
//                                }
//                            }
//
//                            override fun onFailure(call: Call<AttendData>, t: Throwable) {
//                                TODO("Not yet implemented")
//                            }
//                        }) // attend_retrofit
                    }// onclick
                }) // positive
                setNegativeButton("취소",null)
                show()
            }
        }



        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }



    }// onCreate

}