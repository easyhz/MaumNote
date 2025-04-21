package com.maum.note.domain.user.model.response

import java.time.LocalDateTime

data class UserResponseResult(
    val userId: String,
    val creationTime: LocalDateTime
)
