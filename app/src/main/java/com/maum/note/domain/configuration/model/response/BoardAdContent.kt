package com.maum.note.domain.configuration.model.response

import com.maum.note.core.model.setting.BoardAdContent

data class BoardAdContent(
    val directUrl: String,
    val imageUrl: String
) {
    fun toModel() = BoardAdContent(
        directUrl = directUrl,
        imageUrl = imageUrl
    )
}
