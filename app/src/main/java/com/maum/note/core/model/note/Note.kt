package com.maum.note.core.model.note

import java.time.LocalDateTime

data class Note(
    val id: Long,
    val noteType: NoteType,
    val ageType: AgeType,
    val sentenceCountType: SentenceType,
    val inputContent: String,
    val result: String,
    val createdAt: LocalDateTime,
)
