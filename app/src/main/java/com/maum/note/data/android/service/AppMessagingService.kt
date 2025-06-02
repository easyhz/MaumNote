package com.maum.note.data.android.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.maum.note.core.common.helper.log.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppMessagingService: FirebaseMessagingService() {

    private val tag = "AppMessagingService"

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var notificationService: NotificationService

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        logger.d(tag, "message notification: ${message.notification}")
        logger.d(tag, "message data: ${message.data}")
        notificationService.showNotification(context = this, message = message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        logger.d(tag, "onNewToken: $token")

    }
}