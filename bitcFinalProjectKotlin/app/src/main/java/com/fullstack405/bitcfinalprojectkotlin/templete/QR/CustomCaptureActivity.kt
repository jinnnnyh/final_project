package com.fullstack405.bitcfinalprojectkotlin.templete.QR

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.journeyapps.barcodescanner.CaptureActivity
import com.fullstack405.bitcfinalprojectkotlin.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory


class CustomCaptureActivity : CaptureActivity() {
    private lateinit var barcodeView: BarcodeView

    private val callback = BarcodeCallback { result ->
        val scanResult = result.text
        Toast.makeText(this, "스캔된 QR 코드: $scanResult", Toast.LENGTH_LONG).show()

        // 결과를 반환하고 액티비티 종료
        val resultIntent = Intent()
        resultIntent.putExtra("SCAN_RESULT", scanResult)
        setResult(RESULT_OK, resultIntent)
        finish() // 액티비티 종료
    }

    private fun isQRCode(scanResult: String?): Boolean {
        return true // 필요한 로직 추가
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_capture)
        Log.d("CustomCaptureActivity", "CustomCaptureActivity start")
        // 바코드 뷰 초기화
        barcodeView = findViewById(R.id.barcode_view)
        try {
            initScanner()
        } catch (e: Exception) {
            Log.e("CustomCaptureActivity", "Error initializing scanner: ${e.message}")
        }
    }
    private fun initScanner() {
        // 바코드 뷰 설정
        barcodeView.decoderFactory = DefaultDecoderFactory(arrayListOf(BarcodeFormat.QR_CODE, BarcodeFormat.EAN_13))
        barcodeView.cameraSettings.requestedCameraId = 0 // 기본 카메라 사용
        barcodeView.cameraSettings.isAutoFocusEnabled = true
        barcodeView.decodeContinuous(callback) // 지속적으로 스캔
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume() // 카메라 프리뷰 재개
    }

    override fun onPause() {
        super.onPause()
        // 카메라 정지
        barcodeView.stopDecoding()
    }
}
