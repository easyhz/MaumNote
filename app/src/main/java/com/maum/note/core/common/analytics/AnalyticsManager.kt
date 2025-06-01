package com.maum.note.core.common.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.maum.note.core.common.analytics.event.AnalyticsEventInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AnalyticsManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(event: AnalyticsEventInterface) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseAnalytics.logEvent(event.log, null)
        }
    }
}
