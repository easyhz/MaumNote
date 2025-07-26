package com.maum.note.domain.user.model.response

import com.maum.note.core.model.note.AgeType
import java.time.LocalDateTime

data class User(
    val id: String,
    val nickname: String? = null,
    val hasAgreedToTerms: Boolean = false,
    val studentAge: String = AgeType.MIXED.alias,
    val createdAt: LocalDateTime,
    val isDeleted: Boolean = false
)