package com.maum.note.data.android.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.maum.note.MainActivity
import com.maum.note.R
import javax.inject.Inject

class NotificationService @Inject constructor(

) {
    fun showNotification(context: Context, message: RemoteMessage) {
        val messageData = getNotificationData(message) ?: return
        val title = messageData.title
        val body = messageData.body

        val pendingIntent = getDefaultIntent(context = context)
        val notification = createNotification(
            context = context,
            title = title,
            message = body,
            channelId = CHANNEL_ID,
            intent = pendingIntent
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(APP_DEFAULT_NOTIFICATION_ID, notification)
    }

    private fun getNotificationData(remoteMessage: RemoteMessage): NotificationData? {
        if(remoteMessage.notification == null) return null
        return remoteMessage.notification?.let {
             NotificationData(
                title = it.title ?: "",
                body = it.body ?: ""
            )
        }
    }

    private fun createNotification(
        context: Context,
        title: String,
        message: String,
        channelId: String,
        intent: Intent
    ): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)


        return builder.build()
    }

    private fun getDefaultIntent(context: Context): Intent {
        return Intent(
            context,
            MainActivity::class.java
        ).apply {
            `package` = context.packageName
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    companion object {
        const val APP_DEFAULT_NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "MaumNote_Default_Notification_Channel"
        val CHANNEL_NAME = R.string.notification_default_channel_name
    }

    data class NotificationData(
        val title: String,
        val body: String
    )
}