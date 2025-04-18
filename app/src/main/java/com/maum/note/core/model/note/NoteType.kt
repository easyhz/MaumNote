package com.maum.note.core.model.note

enum class NoteType {
    PLAY_CONTEXT,
    LETTER_GREETING,
    ANNOUNCEMENT_CONTENT;

    companion object {
        fun getByValue(value: String): NoteType? {
            return entries.find { it.name == value }
        }
    }
}