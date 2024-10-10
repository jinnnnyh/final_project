package com.fullstack405.bitcfinalprojectkotlin.templete.attend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityQrViewBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Date


///////////// 사용 xxxxxxxxxxxxx
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
        var userId = intent.getLongExtra("userId",0)
        var eventId = intent.getLongExtra("evnetId",0)
        var scheduleId = intent.getLongExtra("scheduleId",0)


        // 큐알코드 생성
        var content = "${eventId}-${scheduleId}-${userId}"
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 512, 512)

        binding.imgQr.setImageBitmap(bitmap)

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}