package com.maum.note.data.board.datasource.remote

import com.maum.note.core.supabase.service.board.dto.post.PostDto

interface PostRemoteDataSource {
    suspend fun insertPost(postDto: PostDto)
}