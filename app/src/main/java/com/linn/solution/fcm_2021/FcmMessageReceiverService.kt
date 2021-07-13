package com.linn.solution.fcm_2021

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class FcmMessageReceiverService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        addNotification(remoteMessage)
    }

    private fun addNotification(remoteMessage: RemoteMessage) {
        val notifyIntent = Intent(remoteMessage.notification!!.clickAction).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, getString(R.string.app_name)).apply {
            setContentIntent(notifyPendingIntent)
        }
        with(NotificationManagerCompat.from(this)) {
            notify(Random().nextInt(), builder.build())
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("LogData",token)
    }

    fun getBitmap(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}