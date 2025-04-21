package com.maum.note.core.common.util.date

import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DateTimeFormatter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")

    fun convertDateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

    fun convertStringToDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    fun formatTimestampToDateTime(timestamp: Timestamp): LocalDateTime {
        return LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault())
    }

    fun formatLocalDateTimeToTimestamp(localDateTime: LocalDateTime): Timestamp {
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        return Timestamp(Date.from(instant))
    }
}