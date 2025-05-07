package com.maum.note.core.common.util.date

import android.content.Context
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun LocalDateTime.toDisplayTimeAgo(
    context: Context,
    appDateTimeFormatter: AppDateTimeFormatter = AppDateTimeFormatter(),
): String {
    return when (val timeType = toTimeAgoType()) {
        is TimeType.JustNow -> context.getString(timeType.stringRes)
        is TimeType.MinutesAgo -> context.getString(timeType.stringRes, timeType.minutes)
        is TimeType.HoursAgo -> context.getString(timeType.stringRes, timeType.hours)
        is TimeType.Yesterday -> context.getString(timeType.stringRes)
        is TimeType.Date -> context.getString(
            timeType.stringRes,
            appDateTimeFormatter.convertDateTimeToStringDate(timeType.date),
        )
    }
}

private fun LocalDateTime.toTimeAgoType(): TimeType {
    val now = LocalDateTime.now()
    val minutes = ChronoUnit.MINUTES.between(this, now)
    val hours = ChronoUnit.HOURS.between(this, now)
    val days = ChronoUnit.DAYS.between(this, now)

    return when {
        minutes < 1 -> TimeType.JustNow
        minutes < 60 -> TimeType.MinutesAgo(minutes)
        hours < 24 -> TimeType.HoursAgo(hours)
        days == 1L -> TimeType.Yesterday
        else -> TimeType.Date(this)
    }
}
