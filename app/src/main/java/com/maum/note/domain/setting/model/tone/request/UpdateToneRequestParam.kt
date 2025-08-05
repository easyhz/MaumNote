package com.maum.note.domain.setting.model.tone.request

data class UpdateToneRequestParam(
    val toneId: String?,
    val noteType: String,
    val content: String,
)
