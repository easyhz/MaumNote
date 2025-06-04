package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R
import com.maum.note.core.common.analytics.event.AddNoteAnalyticsEvent
import com.maum.note.core.common.analytics.event.OnboardingAnalyticsEvent

enum class AgeType(
    @StringRes val title: Int
) {
    MIXED(
        title = R.string.age_type_mixed
    ), ZERO(
        title = R.string.age_type_zero
    ), ONE(
        title = R.string.age_type_one
    ), TWO(
        title = R.string.age_type_two
    ), THREE(
        title = R.string.age_type_three
    ), FOUR(
        title = R.string.age_type_four
    ), FIVE(
        title = R.string.age_type_five
    );

    companion object {
        fun getByValue(value: String): AgeType? {
            return AgeType.entries.find { it.name == value }
        }
    }

    fun getOnboardingLogEvent(): OnboardingAnalyticsEvent {
        return when(this) {
            MIXED -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_MIXED
            ZERO -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_0
            ONE -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_1
            TWO -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_2
            THREE -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_3
            FOUR -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_4
            FIVE -> OnboardingAnalyticsEvent.ONBOARDING_STUDENT_AGE_5
        }
    }

    fun getAddNoteLogEvent(): AddNoteAnalyticsEvent {
        return when(this) {
            MIXED -> AddNoteAnalyticsEvent.STUDENT_AGE_MIXED
            ZERO -> AddNoteAnalyticsEvent.STUDENT_AGE_0
            ONE -> AddNoteAnalyticsEvent.STUDENT_AGE_1
            TWO -> AddNoteAnalyticsEvent.STUDENT_AGE_2
            THREE -> AddNoteAnalyticsEvent.STUDENT_AGE_3
            FOUR -> AddNoteAnalyticsEvent.STUDENT_AGE_4
            FIVE -> AddNoteAnalyticsEvent.STUDENT_AGE_5
        }
    }
}