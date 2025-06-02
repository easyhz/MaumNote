package com.maum.note.core.common.analytics.event

enum class HomeAnalyticsEvent : AnalyticsEventInterface {
    NOTE_COPIED {
        override val log: String
            get() = "home_noteCopied"
    }, NOTE_SELECTED {
        override val log: String
            get() = "home_noteSelected"
    };
}