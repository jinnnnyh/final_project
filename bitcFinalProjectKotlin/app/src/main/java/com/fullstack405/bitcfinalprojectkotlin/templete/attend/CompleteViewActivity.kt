package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.CertificateData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityCompleteViewBinding
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class CompleteViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_complete_view)
        val binding = ActivityCompleteViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 유저id, 이벤트id 필요
        var userId = intent.getLongExtra("userId",0)
        var eventId = intent.getLongExtra("eventId",0)
        var userName = intent.getStringExtra("userName")

        // 참석증 정보 불러오기
        Client.retrofit.findCertificateData(eventId, userId).enqueue(object:retrofit2.Callback<CertificateData>{
            override fun onResponse(call: Call<CertificateData>,response: Response<CertificateData>) {
                val data = response.body() as CertificateData
//                CertificateData(eventTitle=행사 2, appUserName=한태산, presidentName=김운학, schedules=[{scheduleId=2.0, eventDate=2024-10-14}])
                Log.d("findCertificateData", "$data")

                binding.name.text = userName
                binding.title.text = data.eventTitle
                // 여러날이면 기간 인덱스0~ 끝
                if(data.schedules.size == 1){
                    binding.topDate.text = data.schedules[0].get("eventDate").toString()
                }
                else{
                    binding.topDate.text = "${data.schedules[0].get("eventDate").toString()}~${data.schedules[data.schedules.size-1].get("eventDate").toString()}"
                }

                // 수료증 날짜 = 마지막 날의 날짜
                binding.month.text =data.schedules[data.schedules.size-1].get("eventDate").toString().substring(5, 7)
                binding.date.text=data.schedules[data.schedules.size-1].get("eventDate").toString().substring(8, 10)

                binding.president.text = data.presidentName
            }

            override fun onFailure(call: Call<CertificateData>, t: Throwable) {
                Log.d("findCertificateData","error : ${t.message}")
            }

        })



        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}