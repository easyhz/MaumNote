package com.maum.note.core.common.util.date

import com.google.firebase.Timestamp
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object AppDateTimeFormatter {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")

    fun convertDateTimeToStringDate(date: LocalDateTime): String {
        return date.format(dateFormatter)
    }

    fun convertDateTimeToStringDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(timeFormatter)
    }

    fun convertStringToDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    fun formatTimestampToDateTime(timestamp: Timestamp): LocalDateTime {
        return LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault())
    }

    fun formatLocalDateTimeToTimestamp(localDateTime: LocalDateTime): Timestamp {
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        return Timestamp(Date.from(instant))
    }

    fun convertInstantToDateTime(instant: Instant): LocalDateTime {
        return LocalDateTime.ofInstant(instant.toJavaInstant(), ZoneId.systemDefault())
    }

    fun convertDateTimeToInstant(dateTime: LocalDateTime): Instant {
        return Instant.fromEpochMilliseconds(
            dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }
}