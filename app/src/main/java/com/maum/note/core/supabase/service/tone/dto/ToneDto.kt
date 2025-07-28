package com.maum.note.core.supabase.service.tone.dto

import com.maum.note.core.supabase.constant.Table
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToneDto(
    @SerialName(Table.TONES.ID)
    val id: String,
    @SerialName(Table.TONES.USER_ID)
    val userId: String,
    @SerialName(Table.TONES.COMMON)
    val common: String,
    @SerialName(Table.TONES.LETTER_GREETING)
    val letterGreeting: String,
    @SerialName(Table.TONES.PLAY_CONTEXT)
    val playContext: String,
    @SerialName(Table.TONES.ANNOUNCEMENT_CONTENT)
    val announcementContent: String,
    @SerialName(Table.TONES.CREATED_AT)
    val createdAt: Instant = Clock.System.now()
)
