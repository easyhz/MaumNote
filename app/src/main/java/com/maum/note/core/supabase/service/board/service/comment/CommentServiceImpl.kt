package com.maum.note.core.supabase.service.board.service.comment

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.board.dto.comment.CommentDto
import com.maum.note.core.supabase.service.board.dto.comment.CommentWithUserDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class CommentServiceImpl @Inject constructor(
    private val postgrest: Postgrest
): CommentService {
    override suspend fun insertComment(commentDto: CommentDto) {
        postgrest.from(Table.COMMENTS.name).insert(commentDto)
    }

    override suspend fun fetchComments(postId: String): List<CommentWithUserDto> {
        return postgrest.from(Table.COMMENTS.name).select(
            columns = Columns.raw(
                """
                    ${Table.COMMENTS.ID},
                    ${Table.COMMENTS.POST_ID},
                    ${Table.COMMENTS.USER_ID},
                    ${Table.COMMENTS.CONTENT},
                    ${Table.COMMENTS.PARENT_ID},
                    ${Table.COMMENTS.IS_ANONYMOUS},
                    ${Table.COMMENTS.CREATED_AT},
                    ${Table.COMMENTS.IS_DELETED},
                    ${Table.USERS.DTO_NAME}:${Table.USERS.ID_DTO_NAME} (
                        ${Table.USERS.ID},
                        ${Table.USERS.NICKNAME},
                        ${Table.USERS.STUDENT_AGE},
                        ${Table.USERS.FCM_TOKEN},
                        ${Table.USERS.IS_DELETED},
                        ${Table.USERS.HAS_AGREED_TO_TERMS}
                    )
                """.trimIndent()
            ), request = {
                filter {
                    eq(Table.POSTS.ID, postId)
                    eq(Table.POSTS.IS_DELETED, false)
                }
                order(Table.POSTS.CREATED_AT, Order.ASCENDING)
            }
        ).decodeList()
    }
}