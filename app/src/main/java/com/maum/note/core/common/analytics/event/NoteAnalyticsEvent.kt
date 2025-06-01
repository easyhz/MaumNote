package com.maum.note.core.common.analytics.event

enum class NoteAnalyticsEvent : AnalyticsEventInterface {
    NOTE_CREATE {
        override val log: String
            get() = "note_create"
    },
    NOTE_DELETE {
        override val log: String
            get() = "note_delete"
    },
    NOTE_REWRITE {
        override val log: String
            get() = "note_rewrite"
    };
}