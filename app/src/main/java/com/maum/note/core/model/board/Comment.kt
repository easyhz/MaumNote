package com.maum.note.core.model.board

import java.time.LocalDateTime


data class Comment(
    val content: String,
    val author: String,
    val isAnonymous: Boolean,
    val createdAt: LocalDateTime
)