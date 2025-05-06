package com.maum.note.core.firebase.configuration.service

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse


interface ConfigurationService {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
}