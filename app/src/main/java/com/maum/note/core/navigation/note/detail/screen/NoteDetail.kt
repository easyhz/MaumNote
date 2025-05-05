package com.maum.note.core.navigation.note.detail.screen

import com.maum.note.core.common.util.url.urlEncode
import com.maum.note.core.model.note.Note
import com.maum.note.core.navigation.util.serializableType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class NoteDetail(
    val noteDetailArgs: NoteDetailArgs,
) {
    companion object {
        val typeMap = mapOf(
            typeOf<NoteDetailArgs>() to serializableType<NoteDetailArgs>()
        )
    }
}

@Serializable
data class NoteDetailArgs(
    val id: Long,
    val noteType: String,
    val ageType: String,
    val sentenceCountType: String,
    val inputContent: String,
    val result: String,
    val createdAt: String,
)

fun Note.toArgs() =
    NoteDetailArgs(
        id = id,
        noteType = noteType.name,
        ageType = ageType.name,
        sentenceCountType = sentenceCountType.name,
        inputContent = inputContent.urlEncode(),
        result = result.urlEncode(),
        createdAt = createdAt.toString(),
    )
