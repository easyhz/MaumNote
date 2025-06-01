package com.maum.note.core.common.analytics.event

enum class HomeAnalyticsEvent : AnalyticsEventInterface {
    HOME_NOTE_COPIED {
        override val log: String
            get() = "home_noteCopied"
    };
}