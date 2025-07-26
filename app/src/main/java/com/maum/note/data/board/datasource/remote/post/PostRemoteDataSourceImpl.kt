package com.maum.note.data.board.datasource.remote.post

import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto
import com.maum.note.core.supabase.service.board.service.post.PostService
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val postService: PostService
): PostRemoteDataSource {
    override suspend fun insertPost(postDto: PostDto) {
        postService.insertPost(postDto)
    }

    override suspend fun fetchPosts(): List<PostWithCommentDto> {
        return postService.fetchPosts()
    }

    override suspend fun fetchPost(id: String): PostWithCommentDto {
        return postService.fetchPost(id)
    }
}