package com.maum.note.core.model.board

import java.time.LocalDateTime


data class Post(
    val id: String,
    val title: String,
    val content: String,
    val userNickname: String,
    val userId: String,
    val isAnonymous: Boolean,
    val commentCount: Int,
    val createdAt: LocalDateTime
)