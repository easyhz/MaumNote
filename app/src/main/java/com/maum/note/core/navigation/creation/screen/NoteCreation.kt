package com.maum.note.core.navigation.creation.screen

import android.os.Parcelable
import com.maum.note.core.common.util.url.urlEncode
import com.maum.note.core.model.note.generation.GenerationNote
import com.maum.note.core.navigation.util.serializableType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


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
        val generationNoteArgs: GenerationNoteArgs
    ) {
        companion object {
            val typeMap = mapOf(
                typeOf<GenerationNoteArgs>() to serializableType<GenerationNoteArgs>()
            )
        }
    }
}

@Serializable
data class GenerationNoteArgs(
    val noteType: String,
    val sentenceCountType: String,
    val inputContent: String,
)

fun GenerationNote.toArgs() = GenerationNoteArgs(
    noteType = noteType,
    sentenceCountType = sentenceCountType,
    inputContent = inputContent.urlEncode()
)