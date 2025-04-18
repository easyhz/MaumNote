package com.maum.note.core.model.note

enum class AgeType {
    NONE,
    MIXED;

    companion object {
        fun getByValue(value: String): AgeType? {
            return AgeType.entries.find { it.name == value }
        }
    }
}