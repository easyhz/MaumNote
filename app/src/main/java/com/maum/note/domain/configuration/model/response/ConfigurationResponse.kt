package com.maum.note.domain.configuration.model.response

import com.maum.note.core.model.setting.Configuration

data class ConfigurationResponse(
    val androidVersion: String,
    val notionUrl: String,
    val maintenanceNotice: String,
    val announcementContentPrompt: String,
    val letterGreetingPrompt: String,
    val playContextPrompt: String,
    val boardAdContents: List<BoardAdContent>
) {
    fun toModel() =  Configuration(
        androidVersion = androidVersion,
        notionUrl = notionUrl,
        maintenanceNotice = maintenanceNotice,
        boardAdContents = boardAdContents.map { it.toModel() }
    )
}
