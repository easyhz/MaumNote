package com.maum.note.core.navigation.note.creation.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
object NoteCreation: Parcelable {
    @Serializable
    data object NoteTypeSelection

    @Serializable
    data class NoteContent(
        val noteType: String,
    )

    @Serializable
    data class NoteGeneration(
        val ageType: String,
        val noteType: String,
        val sentenceCountType: String,
        val inputContent: String,
    )

    @Serializable
    data class NoteResult(
        val id: String,
        val noteType: String,
        val ageType: String,
        val sentenceCountType: String,
        val inputContent: String,
        val result: String,
        val createdAt: String,
    )
}