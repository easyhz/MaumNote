package com.maum.note.domain.board.repository

import androidx.paging.PagingData
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    suspend fun createPost(request: CreatePostRequest): Result<Unit>
    fun fetchPagedPosts(): Flow<PagingData<Post>>
    suspend fun fetchPosts(): Result<List<Post>>
    suspend fun fetchPost(id: String): Result<Post>
    suspend fun deletePost(id: String): Result<Unit>

    suspend fun createComment(request: CreateCommentRequest): Result<Unit>
    suspend fun fetchComments(postId: String): Result<List<Comment>>
    suspend fun deleteComment(id: String): Result<Unit>

    fun getAnonymousSettingFlow(): Flow<Boolean>
    suspend fun setAnonymousSetting(isAnonymous: Boolean): Result<Unit>
}