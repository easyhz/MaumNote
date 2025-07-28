package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R
import com.maum.note.core.common.analytics.event.AddNoteAnalyticsEvent

enum class SentenceType(
    @StringRes val title: Int,
    val alias: String
) {
    TWO_TO_THREE(
        title = R.string.sentence_two_to_three,
        alias = "two_to_three"
    ),
    FOUR_TO_FIVE(
        title = R.string.sentence_four_to_five,
        alias = "four_to_five"
    ), OVER_TEN(
        title = R.string.sentence_over_ten,
        alias = "ten_or_more"
    );

    companion object {
        fun getByValue(value: String): SentenceType? {
            return entries.find { it.name == value }
        }
    }

    fun getAddNoteLogEvent(): AddNoteAnalyticsEvent {
        return when(this) {
            TWO_TO_THREE -> AddNoteAnalyticsEvent.SENTENCE_COUNT_TWO_TO_THREE
            FOUR_TO_FIVE -> AddNoteAnalyticsEvent.SENTENCE_COUNT_FOUR_TO_FIVE
            OVER_TEN -> AddNoteAnalyticsEvent.SENTENCE_COUNT_TEN_OR_MORE
        }
    }
}