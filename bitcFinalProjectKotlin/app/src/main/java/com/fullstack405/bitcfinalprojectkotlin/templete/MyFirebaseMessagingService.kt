package com.fullstack405.bitcfinalprojectkotlin.templete

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.templete.main.MainActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // 토큰이 발급되거나 갱신되었을 때 호출
        Log.d("token","토큰 발행 성공 : ${FirebaseMessaging.getInstance().token}")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // FCM 메시지를 수신했을 때 처리
        Log.d("FCM", "Message received: ${remoteMessage.data}")

        // Notification 메시지를 수신할 경우는
        // remoteMessage.notification?.body!! 여기에 내용이 저장되어있다.
        // Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)

        if(remoteMessage.data.isNotEmpty()){
            Log.d("aramMsg: ", remoteMessage.data["body"].toString())
            Log.d("aramMsg: ", remoteMessage.data["title"].toString())

            showNotification(remoteMessage)
        }

        else {
            Log.d("aramMsg: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.d("aramMsg: ", remoteMessage.data.toString())
        }
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent
        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임 ? 이거 쓸려면 자동로그인 해야하지않음 ?
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 푸쉬 알림 클릭하면 설정해놓은 액티비티 제일 앞으로 나옴
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        // 알림 채널 이름
        val channelId = "default_channel"

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // channelId = 채널의 고유 아이디, channelName = 사용자가 알아볼 수 있도록 이름 지어줌
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }


        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }

}