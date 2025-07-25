package com.maum.note.domain.board.model.request

data class CreatePostRequest(
    val title: String,
    val content: String,
    val isAnonymous: Boolean
)
