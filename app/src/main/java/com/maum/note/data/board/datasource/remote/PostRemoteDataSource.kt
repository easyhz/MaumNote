package com.maum.note.data.board.datasource.remote

import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto

interface PostRemoteDataSource {
    suspend fun insertPost(postDto: PostDto)
    suspend fun fetchPosts(): List<PostWithCommentDto>
}