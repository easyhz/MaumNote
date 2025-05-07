package com.maum.note.core.common.util.date

import androidx.annotation.StringRes
import com.maum.note.R
import java.time.LocalDateTime

sealed class TimeType(
    @StringRes val stringRes: Int,
) {
    data object JustNow : TimeType(
        stringRes = R.string.time_just_now,
    )
    data class MinutesAgo(val minutes: Long) : TimeType(
        stringRes = R.string.time_minutes_ago,
    )
    data class HoursAgo(val hours: Long) : TimeType(
        stringRes = R.string.time_hours_ago,
    )
    data object Yesterday : TimeType(
        stringRes = R.string.time_yesterday,
    )
    data class Date(val date: LocalDateTime) : TimeType(
        stringRes = R.string.time_date,
    )
}