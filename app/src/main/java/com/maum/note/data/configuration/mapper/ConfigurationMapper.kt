package com.maum.note.data.configuration.mapper

import com.maum.note.domain.configuration.model.response.BoardAdContent
import com.maum.note.domain.configuration.model.response.ConfigurationResponse


class ConfigurationMapper {
    fun mapToResponse(
        data: com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
    ): ConfigurationResponse {
        return ConfigurationResponse(
            androidVersion = data.androidVersion,
            notionUrl = data.notionUrl,
            maintenanceNotice = data.maintenanceNotice,
            announcementContentPrompt = data.announcementContentPrompt,
            letterGreetingPrompt = data.letterGreetingPrompt,
            playContextPrompt = data.playContextPrompt,
            boardAdContents = data.boardAdContents.map { mapToResponse(it) }
        )
    }

    fun mapToResponse(
        data: com.maum.note.core.firebase.configuration.model.response.BoardAdContentsResponse
    ): BoardAdContent = BoardAdContent(
        imageUrl = data.imageUrl,
        directUrl = data.directUrl
    )
}