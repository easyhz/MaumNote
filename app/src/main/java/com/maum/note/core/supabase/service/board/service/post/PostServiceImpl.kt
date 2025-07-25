package com.maum.note.core.supabase.service.board.service.post

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.board.dto.post.PostDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class PostServiceImpl @Inject constructor(
    private val postgrest: Postgrest
): PostService {

    override suspend fun insertPost(postDto: PostDto) {
        postgrest.from(Table.POSTS.name).insert(postDto)
    }
}