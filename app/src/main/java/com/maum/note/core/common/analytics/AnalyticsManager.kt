package com.maum.note.core.common.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.maum.note.core.common.analytics.event.AnalyticsEventInterface

object AnalyticsManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(event: AnalyticsEventInterface) {
        firebaseAnalytics.logEvent(event.log, null)
    }
}
