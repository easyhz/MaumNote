package com.maum.note.core.supabase.service.board.dto.post

import com.maum.note.core.supabase.constant.Table
import kotlinx.serialization.SerialName

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
    val createdAt: String,
    @SerialName(Table.POSTS.IS_DELETED)
    val isDeleted: Boolean
)
