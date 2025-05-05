package com.maum.note.core.navigation.creation.screen

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
        val generationParam: GenerationArgs
    )
}

@Serializable
data class GenerationArgs(
    val noteType: String,
    val sentenceCount: String,
    val inputContent: String,
)