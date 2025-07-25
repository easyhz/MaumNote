package com.maum.note.domain.board.repository

import com.maum.note.core.model.board.Post
import com.maum.note.domain.board.model.request.CreatePostRequest

interface BoardRepository {
    suspend fun createPost(request: CreatePostRequest): Result<Unit>
    suspend fun fetchPosts(): Result<List<Post>>
}