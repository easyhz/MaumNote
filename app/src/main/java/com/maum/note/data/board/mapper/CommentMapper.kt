package com.maum.note.data.board.mapper

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.Generate
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto
import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto
import com.maum.note.domain.board.model.request.CreatePostRequest
import kotlinx.datetime.Clock
import javax.inject.Inject

class CommentMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
) {
    fun toCommentDto() { }

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