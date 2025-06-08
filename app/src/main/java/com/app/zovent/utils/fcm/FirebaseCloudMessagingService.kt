package com.app.zovent.utils.fcm


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.zovent.utils.Constants
import com.app.zovent.utils.PreferenceEntity
import com.app.zovent.utils.Preferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import kotlin.text.get

class FirebaseCloudMessagingService:FirebaseMessagingService() {
    var TAG="Tag"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("TAG", "onNewToken: $token")
        Preferences.setStringPreference(this, PreferenceEntity.NOTIFICATION, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        try {
            Log.e(TAG, "onMessageReceived: " + Gson().toJson(remoteMessage.notification))
            Log.e(TAG, "getData: " + Gson().toJson(remoteMessage.data))
            Preferences.setStringPreference(this, PreferenceEntity.PAYMENT_INFO_DATA, remoteMessage.data["payment_info"]!!)
            Log.d(TAG, "onMessageReceived: " + remoteMessage.from)
            Log.d(TAG, "onMessageReceived: " + remoteMessage.messageId)
            Log.d(TAG, "onMessageReceived: " + remoteMessage.from)
            Log.d(TAG, "onMessageReceived: " + remoteMessage.to)
            onForegroundMessage(remoteMessage)
                val intentData = Intent(Constants.NOTIFICATION)
            intentData.putExtra(Constants.NOTIFICATION, remoteMessage.notification?.body)
            sendBroadcast(intentData)


        } catch (exception: Exception) {
            Log.e(TAG, "onMessageReceived: Error: $exception");
        }

    }

    private fun onForegroundMessage(remoteMessage: RemoteMessage) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        val channelId = "Default"
//        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle(remoteMessage.notification?.title)
//            .setContentText(remoteMessage.notification?.body)
//            .setAutoCancel(true)
//            .setContentIntent(pendingIntent)
//        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "Default channel",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            manager.createNotificationChannel(channel)
//        }
//        manager.notify(0, builder.build())
    }

}