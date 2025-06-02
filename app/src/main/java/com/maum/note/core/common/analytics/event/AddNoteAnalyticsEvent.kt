package com.maum.note.core.common.analytics.event

enum class AddNoteAnalyticsEvent : AnalyticsEventInterface {
    NOTE_TYPE_LETTER_GREETING {
        override val log: String
            get() = "addNote_noteType_letterGreeting"
    },
    NOTE_TYPE_ANNOUNCEMENT_CONTENT {
        override val log: String
            get() = "addNote_noteType_announcementContent"
    },
    NOTE_TYPE_PLAY_CONTEXT {
        override val log: String
            get() = "addNote_noteType_playContext"
    },
    SENTENCE_COUNT_TWO_TO_THREE {
        override val log: String
            get() = "addNote_sentenceCount_twoToThree"
    },
    SENTENCE_COUNT_FOUR_TO_FIVE {
        override val log: String
            get() = "addNote_sentenceCount_fourToFive"
    },
    SENTENCE_COUNT_TEN_OR_MORE {
        override val log: String
            get() = "addNote_sentenceCount_tenOrMore"
    }, STUDENT_AGE_0 {
        override val log: String
            get() = "addNote_studentAge_0"
    }, STUDENT_AGE_1 {
        override val log: String
            get() = "addNote_studentAge_1"
    }, STUDENT_AGE_2 {
        override val log: String
            get() = "addNote_studentAge_2"
    }, STUDENT_AGE_3 {
        override val log: String
            get() = "addNote_studentAge_3"
    }, STUDENT_AGE_4 {
        override val log: String
            get() = "addNote_studentAge_4"
    }, STUDENT_AGE_5 {
        override val log: String
            get() = "addNote_studentAge_5"
    }
    ;
}