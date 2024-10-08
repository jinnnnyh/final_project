package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.AttendTabFragmentAdapter
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityAttendListBinding
import com.google.android.material.tabs.TabLayoutMediator

class AttendListActivity : AppCompatActivity() {
    lateinit var attendTabFragmentAdapter: AttendTabFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_attend_list)
        val binding = ActivityAttendListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = intent.getLongExtra("userId",0)

        // 프레그먼트 어댑터 생성
        attendTabFragmentAdapter = AttendTabFragmentAdapter(this)
        binding.viewPager2.adapter = attendTabFragmentAdapter

        val tabElement: List<String> = mutableListOf("전체", "수료", "미수료")
        try {
            // 탭 연결
            TabLayoutMediator(binding.tablayout, binding.viewPager2) { tab, position ->
                val textView = TextView(this@AttendListActivity)
                textView.text = tabElement[position]
                tab.customView = textView
                textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                textView.setTypeface(textView.typeface, Typeface.BOLD)
//        tab.text. = View.TEXT_ALIGNMENT_CENTER
            }.attach()
        } catch (e: Exception) {
            Log.e("TabLayoutError", "Error in TabLayoutMediator: ${e.message}")
        }


        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    } // onCreate
}