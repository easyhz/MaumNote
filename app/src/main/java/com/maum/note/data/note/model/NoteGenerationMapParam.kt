package com.maum.note.data.note.model

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam

data class NoteGenerationMapParam(
    val noteGenerationRequestParam: NoteGenerationRequestParam,
    val defaultTone: String,
    val typeTone: String,
    val configuration: ConfigurationResponse?
) {
    fun toInputRequestMapParam(): InputRequestMapParam {
        return InputRequestMapParam(
            noteType = getEnumOrThrow(noteGenerationRequestParam.noteType, NoteType::getByValue),
            ageType = getEnumOrThrow(noteGenerationRequestParam.ageType, AgeType::getByValue),
            sentenceType = getEnumOrThrow(noteGenerationRequestParam.sentenceCount, SentenceType::getByValue),
            inputContent = noteGenerationRequestParam.inputContent,
            defaultTone = defaultTone,
            typeTone = typeTone,
            configuration = configuration
        )
    }

    private inline fun <reified T> getEnumOrThrow(
        value: String,
        crossinline getter: (String) -> T?
    ): T = getter(value)
        ?: throw IllegalArgumentException("Invalid ${T::class.simpleName?.lowercase()} value: $value")
}

data class InputRequestMapParam(
    val noteType: NoteType,
    val ageType: AgeType,
    val sentenceType: SentenceType,
    val inputContent: String,
    val defaultTone: String,
    val typeTone: String,
    val configuration: ConfigurationResponse?,
)
