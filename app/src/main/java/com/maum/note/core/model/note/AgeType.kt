package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

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
}