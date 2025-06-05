package com.maum.note.core.model.note

import androidx.annotation.StringRes
import com.maum.note.R
import com.maum.note.core.common.analytics.event.AddNoteAnalyticsEvent

enum class NoteType(
    @StringRes val title: Int,
    @StringRes val tonePlaceholder: Int,
    @StringRes val toneCaption: Int,
    @StringRes val hint: Int?,
    @StringRes val inputTitle: Int?,
    @StringRes val inputPlaceholder: Int?,
    val enabledSelection: Boolean = true,
    val maxCount: Int = 200,
    val alias: String
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
        alias = "default"
    ),
    LETTER_GREETING(
        title = R.string.note_type_letter_greeting,
        tonePlaceholder = R.string.note_type_letter_greeting_tone_placeholder,
        toneCaption = R.string.note_type_letter_greeting_caption,
        hint = R.string.note_type_letter_greeting_hint,
        inputTitle = R.string.note_type_letter_greeting_input_title,
        inputPlaceholder = R.string.note_type_letter_greeting_input_placeholder,
        alias = "letterGreeting"
    ),
    ANNOUNCEMENT_CONTENT(
        title = R.string.note_type_announcement_content,
        tonePlaceholder = R.string.note_type_announcement_content_tone_placeholder,
        toneCaption = R.string.note_type_announcement_content_caption,
        hint = null,
        inputTitle = R.string.note_type_announcement_content_input_title,
        inputPlaceholder = R.string.note_type_announcement_content_input_placeholder,
        alias = "announcementContent"
    ),
    PLAY_CONTEXT(
        title = R.string.note_type_play_context,
        tonePlaceholder = R.string.note_type_play_context_tone_placeholder,
        toneCaption = R.string.note_type_play_context_caption,
        hint = R.string.note_type_play_context_hint,
        inputTitle = R.string.note_type_play_context_input_title,
        inputPlaceholder = R.string.note_type_play_context_input_placeholder,
        alias = "playContext"
    ),;

    companion object {
        fun getByValue(value: String): NoteType? {
            return entries.find { it.name == value }
        }
    }

    fun getAddNoteLogEvent(): AddNoteAnalyticsEvent? {
        return when(this) {
            DEFAULT -> null
            LETTER_GREETING ->  AddNoteAnalyticsEvent.NOTE_TYPE_LETTER_GREETING
            ANNOUNCEMENT_CONTENT -> AddNoteAnalyticsEvent.NOTE_TYPE_ANNOUNCEMENT_CONTENT
            PLAY_CONTEXT -> AddNoteAnalyticsEvent.NOTE_TYPE_PLAY_CONTEXT
        }
    }
}