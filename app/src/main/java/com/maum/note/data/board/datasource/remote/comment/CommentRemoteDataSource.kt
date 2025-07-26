package com.maum.note.data.board.datasource.remote.comment

import com.maum.note.core.supabase.service.board.dto.comment.CommentDto
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto

interface CommentRemoteDataSource {
    suspend fun insertComment(commentDto: CommentDto)
    suspend fun fetchComments(postId: String): List<CommentWithUserDto>
    suspend fun deleteComment(id: String)
}