package com.fullstack405.bitcfinalprojectkotlin.templete.QR
import android.os.Bundle
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.content.Intent
import android.widget.Toast

class QrScannerActivity : CaptureActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 필요한 UI 설정을 추가합니다.
    }

    // 스캔된 QR 코드 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 스캔 성공하면
        if (resultCode == RESULT_OK) {
            val scanResult = data?.getStringExtra("SCAN_RESULT")
            // 데이터 들고 이전 액티비티로 감
            val intent = Intent()
            intent.putExtra("QR_data",scanResult)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    private fun isQRCode(scanResult: String?): Boolean {
        // QR 코드 확인 로직 (필요시 추가)
        return true
    }
}
