package com.maum.note.core.common.analytics.event

enum class SettingAnalyticsEvent : AnalyticsEventInterface {
    SETTING_STUDENT_AGE_CHANGED {
        override val log: String
            get() = "setting_studentAge_changed"
    },
    SETTING_TONE_SAVE {
        override val log: String
            get() = "setting_toneSave"
    };
}