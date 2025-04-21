package com.maum.note.core.common.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    fun init(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }
}