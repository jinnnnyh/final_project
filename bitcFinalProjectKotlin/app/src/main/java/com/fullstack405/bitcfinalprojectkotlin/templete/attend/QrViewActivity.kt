package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrViewBinding
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Date

class QrViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_qr_view)
        val binding = ActivityQrViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        var userId = intent.getLongExtra("userId",0)
//        var eventId = intent.getLongExtra("evnetId",0)
//        var eventDate = intent.getStringExtra("eventDate")
//
//        // 큐알코드 오픈일
//        var openDate = parseInt(eventDate)-7
//
//        // 오늘 날짜 계산
//        var sysDate = Date(System.currentTimeMillis())
//        var dateFormat = SimpleDateFormat("yyyyMMdd")
//        var today = parseInt(dateFormat.format(sysDate))
//
//        binding.imgQr.isVisible = false
//
//        if(openDate == today){
//            binding.imgQr.isVisible = true
//        }
        // 큐알코드를 요청하면 이미지로 주는건가 ??


        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}