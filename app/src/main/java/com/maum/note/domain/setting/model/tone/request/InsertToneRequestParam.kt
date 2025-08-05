package com.maum.note.domain.setting.model.tone.request

data class InsertToneRequestParam(
    val id: String?,
    val userId: String,
    val common: String,
    val letterGreeting: String,
    val playContext: String,
    val announcementContent: String,
)