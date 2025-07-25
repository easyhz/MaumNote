package com.maum.note.data.board.mapper

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.Generate
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.model.board.Post
import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto
import com.maum.note.domain.board.model.request.CreatePostRequest
import kotlinx.datetime.Clock
import javax.inject.Inject

class PostMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
) {
    fun toPostDto(
        request: CreatePostRequest,
        userId: String,
    ): PostDto = PostDto(
        id = Generate.randomUUIDv7(),
        userId = userId,
        title = request.title,
        content = request.content,
        createdAt = Clock.System.now(),
        isAnonymous = request.isAnonymous,
        isDeleted = false,
    )

    fun toPost(
        dto: PostWithCommentDto
    ): Post = Post(
        id = dto.id,
        author = dto.user.nickname ?: resourceHelper.getString(R.string.board_anonymous),
        title = dto.title,
        content = dto.content,
        isAnonymous = dto.isAnonymous,
        commentCount = dto.commentCount.firstOrNull()?.count ?: 0,
        createdAt = AppDateTimeFormatter.convertInstantToDateTime(dto.createdAt),
    )
}