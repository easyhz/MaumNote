package com.maum.note.core.common.analytics.event

enum class AppAnalyticsEvent : AnalyticsEventInterface {
    APP_LAUNCH {
        override val log: String
            get() = "app_launch"
    };
}