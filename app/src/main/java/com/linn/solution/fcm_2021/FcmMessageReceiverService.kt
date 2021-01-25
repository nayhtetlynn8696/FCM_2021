package com.linn.solution.fcm_2021

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class FcmMessageReceiverService:FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage : RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isEmpty()){
            showNotification(remoteMessage.notification!!.title.toString(),
                remoteMessage.notification!!.body.toString()
            )
        }else{
            showNotification(remoteMessage.data as HashMap<String, String>)
        }
    }

    private fun showNotification(data: HashMap<String, String>){
        val title= data["title"].toString()
        val body= data["message"].toString()
        val id=data["userId"].toString()
        Log.d("LogData",id)
        val channel_id=getString(R.string.app_name)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uri)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentTitle(title)
            .setContentText(body)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id,
                "Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.setSound(uri, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(Random().nextInt(), builder.build())
    }

    private fun showNotification(title:String, body:String){
        val channel_id=getString(R.string.app_name)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uri)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentTitle(title)
            .setContentText(body)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id,
                "Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.setSound(uri, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(Random().nextInt(), builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("Tokennnnnnn", token)
    }
}