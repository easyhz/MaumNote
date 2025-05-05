package com.maum.note.domain.note.model.request

data class CreateNoteRequestParam(
    val noteType: String,
    val age: String,
    val sentenceCount: String,
    val inputContent: String,
)
