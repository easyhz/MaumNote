package com.maum.note.data.board.datasource.remote.comment

import com.maum.note.core.supabase.service.board.dto.comment.CommentDto
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto
import com.maum.note.core.supabase.service.board.service.comment.CommentService
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val commentService: CommentService
): CommentRemoteDataSource {
    override suspend fun insertComment(commentDto: CommentDto) {
        return commentService.insertComment(commentDto)
    }

    override suspend fun fetchComments(postId: String): List<CommentWithUserDto> {
        return commentService.fetchComments(postId)
    }

    override suspend fun deleteComment(id: String) {
        return commentService.deleteComment(id)
    }
}