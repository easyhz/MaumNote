package com.maum.note.core.common.analytics.event

enum class NoteDetailAnalyticsEvent : AnalyticsEventInterface {
    NOTE_DETAIL_COPIED {
        override val log: String
            get() = "noteDetail_copied"
    },
    NOTE_RATE_1 {
        override val log: String
            get() = "noteDetail_noteRated_1"
    },
    NOTE_RATE_2 {
        override val log: String
            get() = "noteDetail_noteRated_2"
    },
    NOTE_RATE_3 {
        override val log: String
            get() = "noteDetail_noteRated_3"
    },
    NOTE_RATE_4 {
        override val log: String
            get() = "noteDetail_noteRated_4"
    },
    NOTE_RATE_5 {
        override val log: String
            get() = "noteDetail_noteRated_5"
    };
}