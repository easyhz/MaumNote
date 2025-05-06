package com.maum.note.core.navigation.note.detail.screen

import kotlinx.serialization.Serializable

@Serializable
data class NoteDetail(
    val id: Long,
    val noteType: String,
    val ageType: String,
    val sentenceCountType: String,
    val inputContent: String,
    val result: String,
    val createdAt: String,
)
