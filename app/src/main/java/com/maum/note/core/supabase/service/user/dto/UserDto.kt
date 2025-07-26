package com.maum.note.core.supabase.service.user.dto

import com.maum.note.core.model.note.AgeType
import com.maum.note.core.supabase.constant.Table
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName(Table.USERS.ID)
    val id: String,
    @SerialName(Table.USERS.NICKNAME)
    val nickname: String? = null,
    @SerialName(Table.USERS.HAS_AGREED_TO_TERMS)
    val hasAgreedToTerms: Boolean = false,
    @SerialName(Table.USERS.STUDENT_AGE)
    val studentAge: String = AgeType.MIXED.alias,
    @SerialName(Table.USERS.CREATED_AT)
    val createdAt: Instant = Clock.System.now(),
    @SerialName(Table.USERS.IS_DELETED)
    val isDeleted: Boolean = false
)