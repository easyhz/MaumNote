package com.maum.note.core.supabase.service.board.service.post

import com.maum.note.core.supabase.service.board.dto.post.PostDto

interface PostService {

    suspend fun insertPost(post: PostDto)
}