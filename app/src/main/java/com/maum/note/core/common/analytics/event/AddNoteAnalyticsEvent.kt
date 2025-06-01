package com.maum.note.core.common.analytics.event

enum class AddNoteAnalyticsEvent : AnalyticsEventInterface {
    NOTE_TYPE_LETTER_GREETING {
        override val log: String
            get() = "noteType_letterGreeting"
    },
    NOTE_TYPE_ANNOUNCEMENT_CONTENT {
        override val log: String
            get() = "noteType_announcementContent"
    },
    NOTE_TYPE_PLAY_CONTEXT {
        override val log: String
            get() = "noteType_playContext"
    },
    SENTENCE_COUNT_TWO_TO_THREE {
        override val log: String
            get() = "sentenceCount_twoToThree"
    },
    SENTENCE_COUNT_FOUR_TO_FIVE {
        override val log: String
            get() = "sentenceCount_fourToFive"
    },
    SENTENCE_COUNT_SIX_TO_NINE {
        override val log: String
            get() = "sentenceCount_tenOrMore"
    };
}