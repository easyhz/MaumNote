package com.maum.note.core.common.util.validation

class ValidationInput {
    fun isValidContentInput(text: String, maxCount: Int): Boolean {
        return text.length <= maxCount
    }
}