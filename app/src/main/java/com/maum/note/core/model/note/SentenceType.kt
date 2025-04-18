package com.maum.note.core.model.note

enum class SentenceType {
    ONE_TO_TWO,
    THREE_TO_FOUR;

    companion object {
        fun getByValue(value: String): SentenceType? {
            return entries.find { it.name == value }
        }
    }
}