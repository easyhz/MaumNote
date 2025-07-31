package com.maum.note.data.board.datasource.remote.post

import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto

interface PostRemoteDataSource {
    suspend fun insertPost(postDto: PostDto)
    suspend fun fetchPosts(from: Long, to: Long): List<PostWithCommentDto>
    suspend fun fetchPost(id: String): PostWithCommentDto
    suspend fun deletePost(id: String)
}