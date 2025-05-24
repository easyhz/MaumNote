package com.maum.note.domain.configuration.model.response

data class ConfigurationResponse(
    val androidVersion: String,
    val notionUrl: String,
    val maintenanceNotice: String,
    val announcementContentPrompt: String,
    val letterGreetingPrompt: String,
    val playContextPrompt: String,
)
