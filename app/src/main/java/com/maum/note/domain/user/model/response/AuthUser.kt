package com.maum.note.domain.user.model.response

import java.time.LocalDateTime

data class AuthUser(
    val id: String,
    val createdAt: LocalDateTime?,
)