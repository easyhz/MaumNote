package com.maum.note.core.supabase.service.board.service.comment

import com.maum.note.core.supabase.service.board.dto.comment.CommentDto
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto

interface CommentService {
    suspend fun insertComment(commentDto: CommentDto)
    suspend fun fetchComments(postId: String): List<CommentWithUserDto>
}