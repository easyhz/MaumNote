package com.maum.note.core.supabase.service.board.dto.post

import com.maum.note.core.supabase.constant.Table
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName(Table.POSTS.ID)
    val id: String,
    @SerialName(Table.POSTS.USER_ID)
    val userId: String,
    @SerialName(Table.POSTS.TITLE)
    val title: String,
    @SerialName(Table.POSTS.CONTENT)
    val content: String,
    @SerialName(Table.POSTS.IS_ANONYMOUS)
    val isAnonymous: Boolean,
    @SerialName(Table.POSTS.CREATED_AT)
    val createdAt: Instant,
    @SerialName(Table.POSTS.IS_DELETED)
    val isDeleted: Boolean = false
)
