package com.maum.note.core.model.board

import java.time.LocalDateTime


data class Comment(
    val id: String,
    val postId: String,
    val content: String,
    val userNickname: String,
    val userId: String,
    val parentId: String?,
    val isAnonymous: Boolean,
    val createdAt: LocalDateTime
)