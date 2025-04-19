package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

enum class NoteType(
    @StringRes val title: Int,
    @StringRes val placeholder: Int,
    @StringRes val caption: Int,
    val enabledSelection: Boolean = true,
) {
    DEFAULT(
        title = R.string.note_type_default,
        placeholder = R.string.note_type_default_placeholder,
        caption = R.string.note_type_default_caption,
        enabledSelection = false
    ),
    LETTER_GREETING(
        title = R.string.note_type_letter_greeting,
        placeholder = R.string.note_type_letter_greeting_placeholder,
        caption = R.string.note_type_letter_greeting_caption
    ),
    PLAY_CONTEXT(
        title = R.string.note_type_play_context,
        placeholder = R.string.note_type_play_context_placeholder,
        caption = R.string.note_type_play_context_caption
    ),
    ANNOUNCEMENT_CONTENT(
        title = R.string.note_type_announcement_content,
        placeholder = R.string.note_type_announcement_content_placeholder,
        caption = R.string.note_type_announcement_content_caption
    );

    companion object {
        fun getByValue(value: String): NoteType? {
            return entries.find { it.name == value }
        }
    }
}