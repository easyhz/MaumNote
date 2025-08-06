package com.maum.note.domain.configuration.model.response

import com.maum.note.core.model.setting.AdContent

data class BoardAdContent(
    val directUrl: String,
    val imageUrl: String
) {
    fun toModel() = AdContent(
        directUrl = directUrl,
        imageUrl = imageUrl
    )
}
