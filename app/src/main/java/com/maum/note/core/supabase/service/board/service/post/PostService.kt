package com.maum.note.core.supabase.service.board.service.post

import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto

interface PostService {
    suspend fun insertPost(post: PostDto)

    suspend fun fetchPosts(from: Long, to: Long): List<PostWithCommentDto>
    suspend fun fetchPost(id: String): PostWithCommentDto

    suspend fun deletePost(id: String)
}