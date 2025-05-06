package com.maum.note.domain.configuration.model.response

data class ConfigurationResponse(
    val androidVersion: String,
    val notionUrl: String,
    val maintenanceNotice: String,
)
