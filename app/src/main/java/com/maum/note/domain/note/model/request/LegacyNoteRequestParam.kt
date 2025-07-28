package com.maum.note.domain.note.model.request

import java.time.LocalDateTime

data class LegacyNoteRequestParam(
    val userId: String,
    val noteType: String,
    val studentAge: String,
    val sentenceCount: String,
    val inputContent: String,
    val result: String,
    val createdAt: LocalDateTime,
    val studentId: Long? = null,
)
