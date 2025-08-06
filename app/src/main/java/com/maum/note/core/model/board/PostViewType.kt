package com.maum.note.core.model.board

enum class PostViewType(
    val titleMaxLine: Int,
    val contentMaxLine: Int
) {
    OVERVIEW(
        titleMaxLine = 1,
        contentMaxLine = 3
    ), DETAIL(
        titleMaxLine = Int.MAX_VALUE,
        contentMaxLine = Int.MAX_VALUE
    )
}