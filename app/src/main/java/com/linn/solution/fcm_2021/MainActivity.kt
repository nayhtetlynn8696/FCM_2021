package com.linn.solution.fcm_2021

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.freshchat.consumer.sdk.Freshchat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().isAutoInitEnabled=true

        FirebaseMessaging.getInstance().subscribeToTopic("news")
        FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("LogData1", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    Freshchat.getInstance(this).setPushRegistrationToken(token!!)
                    Log.d("LogData1", "Token==$token")
                })


        findViewById<MaterialTextView>(R.id.tv_view).setOnClickListener {
            showNoti()
        }

    }

    private fun showNoti(){
        val notifyIntent = Intent(this, ResultActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID").apply {
            setContentTitle("Notifications Example")
            setContentText("This is a test notification")
            setSmallIcon(R.mipmap.ic_launcher)
            setContentIntent(notifyPendingIntent)
        }
        val notificationIntent = Intent(this, ResultActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    private fun addNotification() {
        val builder = NotificationCompat.Builder(this,getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_launcher) //set icon for notification
            .setContentTitle("Notifications Example") //set title of notification
            .setContentText("This is a notification message") //this is notification message
            .setAutoCancel(true) // makes auto cancel of notification
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
        val notificationIntent = Intent(this, ResultActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)

        // Add as notification
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }
}