package com.maum.note.core.model.board

import java.time.LocalDateTime


data class Post(
    val title: String,
    val content: String,
    val author: String,
    val isAnonymous: Boolean,
    val commentCount: Int,
    val hasCommented: Boolean,
    val createdAt: LocalDateTime
)