package com.fullstack405.bitcfinalprojectkotlin.templete.notice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityNoticeDetailBinding
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityNoticeListBinding

class NoticeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_notice_detail)
        val binding = ActivityNoticeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var id = intent.getLongExtra("noticeId",0)
        var title = intent.getStringExtra("notiTitle")
        var content = intent.getStringExtra("notiContent")
        var date = intent.getStringExtra("notiDate")

        binding.dTitle.text = title
        binding.dCreateDate.text = date
        binding.dContent.text = content

        binding.btnBack.setOnClickListener {
            finish()
        }

    }//onCreate
}