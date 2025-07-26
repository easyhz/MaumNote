package com.maum.note.core.navigation.board.screen

import kotlinx.serialization.Serializable


@Serializable
data class PostDetail(
    val id: String,
    val title: String,
)
