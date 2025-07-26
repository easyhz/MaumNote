package com.maum.note.data.board.mapper

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.Generate
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.model.board.Comment
import com.maum.note.core.supabase.service.board.dto.comment.CommentDto
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import kotlinx.datetime.Clock
import javax.inject.Inject

class CommentMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
) {
    fun toCommentDto(
        request: CreateCommentRequest,
        userId: String,
    ): CommentDto = CommentDto(
        id = Generate.randomUUIDv7(),
        postId = request.postId,
        userId = userId,
        content = request.content,
        parentId = request.parentId,
        isAnonymous = request.isAnonymous,
        createdAt = Clock.System.now(),
        isDeleted = false
    )

    fun toComment(
        dto: CommentWithUserDto
    ): Comment = Comment(
        id = dto.id,
        postId = dto.postId,
        userId = dto.userId,
        userNickname = dto.user.nickname ?: resourceHelper.getString(R.string.board_anonymous),
        content = dto.content,
        parentId = dto.parentId,
        isAnonymous = dto.isAnonymous,
        createdAt = AppDateTimeFormatter.convertInstantToDateTime(dto.createdAt),
    )
}