package com.maum.note.domain.board.model.comment.request

data class CreateCommentRequest(
    val postId: String,
    val content: String,
    val parentId: String? = null,
    val isAnonymous: Boolean
)