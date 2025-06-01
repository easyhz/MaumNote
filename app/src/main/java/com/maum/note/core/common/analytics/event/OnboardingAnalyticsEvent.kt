package com.maum.note.core.common.analytics.event

enum class OnboardingAnalyticsEvent: AnalyticsEventInterface {
    ONBOARDING_STUDENT_AGE_MIXED {
        override val log: String
            get() = "onboarding_studentAge_mixed"
    },
    ONBOARDING_STUDENT_AGE_0 {
        override val log: String
            get() = "onboarding_studentAge_0"
    },
    ONBOARDING_STUDENT_AGE_1 {
        override val log: String
            get() = "onboarding_studentAge_1"
    },
    ONBOARDING_STUDENT_AGE_2 {
        override val log: String
            get() = "onboarding_studentAge_2"
    },
    ONBOARDING_STUDENT_AGE_3 {
        override val log: String
            get() = "onboarding_studentAge_3"
    },
    ONBOARDING_STUDENT_AGE_4 {
        override val log: String
            get() = "onboarding_studentAge_4"
    },
    ONBOARDING_STUDENT_AGE_5 {
        override val log: String
            get() = "onboarding_studentAge_5"
    }
}