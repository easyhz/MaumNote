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
}