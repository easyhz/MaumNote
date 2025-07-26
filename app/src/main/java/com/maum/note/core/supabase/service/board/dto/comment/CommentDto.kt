package com.maum.note.core.supabase.service.board.dto.comment

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.user.dto.UserDto
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    @SerialName(Table.COMMENTS.ID)
    val id: String,
    @SerialName(Table.COMMENTS.POST_ID)
    val postId: String,
    @SerialName(Table.COMMENTS.USER_ID)
    val userId: String,
    @SerialName(Table.COMMENTS.PARENT_ID)
    val parentId: String? = null,
    @SerialName(Table.COMMENTS.CONTENT)
    val content: String,
    @SerialName(Table.COMMENTS.IS_ANONYMOUS)
    val isAnonymous: Boolean,
    @SerialName(Table.COMMENTS.CREATED_AT)
    val createdAt: Instant = Clock.System.now(),
    @SerialName(Table.COMMENTS.IS_DELETED)
    val isDeleted: Boolean = false,
)
