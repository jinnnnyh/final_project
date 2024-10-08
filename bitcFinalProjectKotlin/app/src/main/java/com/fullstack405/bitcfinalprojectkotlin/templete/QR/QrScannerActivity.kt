package com.fullstack405.bitcfinalprojectkotlin.templete.QR

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrScannerBinding

class QrScannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_qr_scanner)
        val binding = ActivityQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // qr 코드 스캐너 참고
//        https://velog.io/@onegold-11/Android-zxing-%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-QR-%EC%8A%A4%EC%BA%94-%EA%B8%B0%EB%8A%A5

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }

    }// onCreateㅁ
}