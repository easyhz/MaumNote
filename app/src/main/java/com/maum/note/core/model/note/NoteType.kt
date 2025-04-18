package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

enum class NoteType(
    @StringRes val title: Int
) {
    LETTER_GREETING(
        title = R.string.note_type_letter_greeting
    ),
    PLAY_CONTEXT(
        title = R.string.note_type_play_context
    ),
    ANNOUNCEMENT_CONTENT(
        title = R.string.note_type_announcement_content
    );

    companion object {
        fun getByValue(value: String): NoteType? {
            return entries.find { it.name == value }
        }
    }
}