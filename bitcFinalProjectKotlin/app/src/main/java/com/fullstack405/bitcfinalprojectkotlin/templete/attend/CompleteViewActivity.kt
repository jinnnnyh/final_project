package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityCompleteViewBinding

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

        // 이름, 행사명, 행사날짜(마지막날)
        var name = intent.getStringExtra("userName")
        var eventName = intent.getStringExtra("eventName")
        var eventDate = intent.getStringExtra("eventDate")

        binding.title.text = eventName
        binding.name.text = name
        binding.topDate.text = eventDate
        binding.title2.text = eventName
        binding.month.text = eventDate?.substring(5,7)
        binding.date.text = eventDate?.substring(8,10)

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}