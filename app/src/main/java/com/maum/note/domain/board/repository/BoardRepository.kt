package com.maum.note.domain.board.repository

import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    suspend fun createPost(request: CreatePostRequest): Result<Unit>
    suspend fun fetchPosts(): Result<List<Post>>
    suspend fun fetchPost(id: String): Result<Post>
    suspend fun deletePost(id: String): Result<Unit>

    suspend fun createComment(request: CreateCommentRequest): Result<Unit>
    suspend fun fetchComments(postId: String): Result<List<Comment>>

    fun getAnonymousSettingFlow(): Flow<Boolean>
    suspend fun setAnonymousSetting(isAnonymous: Boolean): Result<Unit>
}