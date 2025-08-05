package com.maum.note.domain.setting.model.tone.request

data class UpdateAllToneRequestParam(
    val toneId: String? = null,
    val common: String,
    val letterGreeting: String,
    val playContext: String,
    val announcementContent: String,
)
