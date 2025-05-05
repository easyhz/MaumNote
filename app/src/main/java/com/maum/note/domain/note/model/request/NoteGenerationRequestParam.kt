package com.maum.note.domain.note.model.request

data class NoteGenerationRequestParam(
    val noteType: String,
    val age: String,
    val sentenceCount: String,
    val inputContent: String,
)
