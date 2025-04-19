package com.maum.note.core.common.util.date

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")

    fun convertDateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

    fun convertStringToDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, formatter)
    }
}