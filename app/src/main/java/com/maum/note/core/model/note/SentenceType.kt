package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

enum class SentenceType(
    @StringRes val title: Int
) {
    ONE_TO_TWO(
        title = R.string.sentence_one_to_two
    ),
    THREE_TO_FOUR(
        title = R.string.sentence_three_to_four
    );

    companion object {
        fun getByValue(value: String): SentenceType? {
            return entries.find { it.name == value }
        }
    }
}