package com.maum.note.data.note.model.request

import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType

data class NoteRequestData(
    val noteType: NoteType,
    val age: AgeType,
    val sentenceCount: SentenceType,
    val inputContent: String,
    val result: String
)
