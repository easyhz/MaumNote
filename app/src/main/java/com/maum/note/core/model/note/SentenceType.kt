package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

enum class SentenceType(
    @StringRes val title: Int
) {
    TWO_TO_THREE(
        title = R.string.sentence_two_to_three
    ),
    FOUR_TO_FIVE(
        title = R.string.sentence_four_to_five
    ), OVER_TEN(
        title = R.string.sentence_over_ten
    );

    companion object {
        fun getByValue(value: String): SentenceType? {
            return entries.find { it.name == value }
        }
    }
}