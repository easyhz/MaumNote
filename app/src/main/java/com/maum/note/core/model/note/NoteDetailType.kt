package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

sealed class NoteDetailType(
    @StringRes val title: Int,
    val content: String,
    val isCopyable: Boolean = true,
) {
    data class Type(val noteType: NoteType, val value: String): NoteDetailType(
        title = noteType.title,
        content = value,
    )
    data class Age(
        val value: String
    ): NoteDetailType(
        title = R.string.note_detail_age,
        content = value,
        isCopyable = false
    )
    data class Sentence(
        val value: String
    ): NoteDetailType(
        title = R.string.note_detail_sentence,
        content = value,
        isCopyable = false
    )
    data class Input(
        val value: String
    ): NoteDetailType(
        title = R.string.note_detail_input,
        content = value,
    )

    companion object {
        fun entries(
            noteType: NoteType,
            typeValue: String,
            ageValue: String,
            sentenceValue: String,
            inputValue: String,
        ) = listOf(
            Type(noteType, typeValue),
            Age(ageValue),
            Sentence(sentenceValue),
            Input(inputValue),
        )

        val empty = entries(
            noteType = NoteType.DEFAULT,
            typeValue = "",
            ageValue = "",
            sentenceValue = "",
            inputValue = "",
        )
    }
}