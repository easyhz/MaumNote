package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R

enum class NoteType(
    @StringRes val title: Int,
    @StringRes val tonePlaceholder: Int,
    @StringRes val toneCaption: Int,
    @StringRes val hint: Int?,
    @StringRes val inputTitle: Int?,
    @StringRes val inputPlaceholder: Int?,
    val enabledSelection: Boolean = true,
    val maxCount: Int = 200
) {
    DEFAULT(
        title = R.string.note_type_default,
        tonePlaceholder = R.string.note_type_default_tone_placeholder,
        toneCaption = R.string.note_type_default_caption,
        enabledSelection = false,
        hint = null,
        inputTitle = null,
        inputPlaceholder = null,
        maxCount = 300,
    ),
    LETTER_GREETING(
        title = R.string.note_type_letter_greeting,
        tonePlaceholder = R.string.note_type_letter_greeting_tone_placeholder,
        toneCaption = R.string.note_type_letter_greeting_caption,
        hint = R.string.note_type_letter_greeting_hint,
        inputTitle = R.string.note_type_letter_greeting_input_title,
        inputPlaceholder = R.string.note_type_letter_greeting_input_placeholder,
    ),
    ANNOUNCEMENT_CONTENT(
        title = R.string.note_type_announcement_content,
        tonePlaceholder = R.string.note_type_announcement_content_tone_placeholder,
        toneCaption = R.string.note_type_announcement_content_caption,
        hint = null,
        inputTitle = R.string.note_type_announcement_content_input_title,
        inputPlaceholder = R.string.note_type_announcement_content_input_placeholder,
    ),
    PLAY_CONTEXT(
        title = R.string.note_type_play_context,
        tonePlaceholder = R.string.note_type_play_context_tone_placeholder,
        toneCaption = R.string.note_type_play_context_caption,
        hint = R.string.note_type_play_context_hint,
        inputTitle = R.string.note_type_play_context_input_title,
        inputPlaceholder = R.string.note_type_play_context_input_placeholder,
    ),;

    companion object {
        fun getByValue(value: String): NoteType? {
            return entries.find { it.name == value }
        }
    }
}