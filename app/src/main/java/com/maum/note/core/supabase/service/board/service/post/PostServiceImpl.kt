package com.maum.note.core.supabase.service.board.service.post

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.board.dto.post.PostDto
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class PostServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : PostService {
    override suspend fun insertPost(postDto: PostDto) {
        postgrest.from(Table.POSTS.name).insert(postDto)
    }

    override suspend fun fetchPosts(): List<PostWithCommentDto> {
        return postgrest.from(Table.POSTS.name).select(
            columns = Columns.raw(
                """
                    ${Table.POSTS.ID},
                    ${Table.POSTS.USER_ID},
                    ${Table.POSTS.TITLE},
                    ${Table.POSTS.CONTENT},
                    ${Table.POSTS.IS_ANONYMOUS},
                    ${Table.POSTS.IS_DELETED},
                    ${Table.POSTS.CREATED_AT},
                    ${Table.USERS.DTO_NAME}:${Table.USERS.ID_DTO_NAME} (
                        ${Table.USERS.ID},
                        ${Table.USERS.NICKNAME},
                        ${Table.USERS.STUDENT_AGE},
                        ${Table.USERS.FCM_TOKEN},
                        ${Table.USERS.IS_DELETED},
                        ${Table.USERS.HAS_AGREED_TO_TERMS}
                    ),
                    ${Table.POSTS.COMMENT_COUNT}: ${Table.COMMENTS.name}(count)
                """.trimIndent()
            ), request = {
                filter {
                    eq(Table.POSTS.IS_DELETED, false)
                }
                order(Table.POSTS.CREATED_AT, Order.DESCENDING)
                range(0, 10)
            }
        ).decodeList<PostWithCommentDto>()
    }

    override suspend fun fetchPost(id: String): PostWithCommentDto {
        return postgrest.from(Table.POSTS.name).select(
            columns = Columns.raw(
                """
                    ${Table.POSTS.ID},
                    ${Table.POSTS.USER_ID},
                    ${Table.POSTS.TITLE},
                    ${Table.POSTS.CONTENT},
                    ${Table.POSTS.IS_ANONYMOUS},
                    ${Table.POSTS.IS_DELETED},
                    ${Table.POSTS.CREATED_AT},
                    ${Table.USERS.DTO_NAME}:${Table.USERS.ID_DTO_NAME} (
                        ${Table.USERS.ID},
                        ${Table.USERS.NICKNAME},
                        ${Table.USERS.STUDENT_AGE},
                        ${Table.USERS.FCM_TOKEN},
                        ${Table.USERS.IS_DELETED},
                        ${Table.USERS.HAS_AGREED_TO_TERMS}
                    ),
                    ${Table.POSTS.COMMENT_COUNT}: ${Table.COMMENTS.name}(count)
                """.trimIndent()
            ), request = {
                filter {
                    eq(Table.POSTS.ID, id)
                    eq(Table.POSTS.IS_DELETED, false)
                }
                order(Table.POSTS.CREATED_AT, Order.DESCENDING)
            }
        ).decodeSingle()
    }
}