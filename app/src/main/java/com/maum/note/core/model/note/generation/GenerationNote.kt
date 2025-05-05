package com.maum.note.core.model.note.generation

data class GenerationNote(
    val noteType: String,
    val sentenceCountType: String,
    val inputContent: String,
)
