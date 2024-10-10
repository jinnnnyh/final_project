package com.fullstack405.bitcfinalprojectkotlin.templete.QR

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrScannerBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DefaultDecoderFactory

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



        var createRequest = "" // 스캔한 값 저장 변수
        val callback = BarcodeCallback { result ->

            var currentTime = System.currentTimeMillis() // 스캐너 켜진 시간

            // 현재 시간 - 스캐너 켜진 시간 >= 3초
            if (System.currentTimeMillis() - currentTime >= 3000) {
                result?.let { it ->
                    createRequest = it.text
                    Toast.makeText(this,"스캔이 완료되었습니다. ${createRequest}",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.barcodeScanner.run {
            barcodeView.decoderFactory = DefaultDecoderFactory(arrayListOf(BarcodeFormat.QR_CODE, BarcodeFormat.EAN_13))
            barcodeView.cameraSettings.requestedCameraId = 1
            barcodeView.cameraSettings.isAutoFocusEnabled = true
            decodeContinuous(callback)
        }

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }

    }// onCreateㅁ
}