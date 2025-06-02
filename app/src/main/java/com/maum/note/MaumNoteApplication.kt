package com.maum.note

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.AppAnalyticsEvent
import com.maum.note.data.android.service.NotificationService
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MaumNoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AnalyticsManager.init(this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )

        val config = ClarityConfig(BuildConfig.CLARITY_PROJECT_ID)
        Clarity.initialize(applicationContext, config)

        createNotificationChannel()
        logEventAppLaunch()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.CHANNEL_ID,
            this.getString(NotificationService.CHANNEL_NAME),
            NotificationManager.IMPORTANCE_HIGH,
        )
        channel.description = getString(R.string.notification_default_channel_description)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun logEventAppLaunch() {
        AnalyticsManager.logEvent(AppAnalyticsEvent.APP_LAUNCH)
    }

}