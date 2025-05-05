package com.maum.note.data.note.model

import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.domain.note.model.request.CreateNoteRequestParam

data class CreateNoteMapParam(
    val createNoteRequestParam: CreateNoteRequestParam,
    val defaultTone: String,
    val typeTone: String,
) {
    fun toInputRequestMapParam(): InputRequestMapParam {
        return InputRequestMapParam(
            noteType = getEnumOrThrow(createNoteRequestParam.noteType, NoteType::getByValue),
            ageType = getEnumOrThrow(createNoteRequestParam.age, AgeType::getByValue),
            sentenceType = getEnumOrThrow(createNoteRequestParam.sentenceCount, SentenceType::getByValue),
            inputContent = createNoteRequestParam.inputContent,
            defaultTone = defaultTone,
            typeTone = typeTone
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
    val typeTone: String
)
