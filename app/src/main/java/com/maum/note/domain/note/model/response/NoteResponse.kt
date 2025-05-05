package com.maum.note.domain.note.model.response

import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import java.time.LocalDateTime

data class NoteResponse(
    val id: Int,
    val noteType: String,
    val ageType: String,
    val sentenceCountType: String,
    val inputContent: String,
    val result: String,
    val createdAt: LocalDateTime,
) {
    fun toNote() = Note(
        id = id,
        noteType = NoteType.getByValue(noteType) ?: NoteType.DEFAULT,
        ageType = AgeType.getByValue(ageType) ?: AgeType.MIXED,
        sentenceCountType = SentenceType.getByValue(sentenceCountType) ?: SentenceType.TWO_TO_THREE,
        inputContent = inputContent,
        result = result,
        createdAt = createdAt,
    )
}