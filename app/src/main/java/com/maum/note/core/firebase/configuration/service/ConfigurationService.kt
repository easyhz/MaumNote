package com.maum.note.core.firebase.configuration.service

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
import com.maum.note.core.firebase.configuration.model.response.SystemPromptResponse


interface ConfigurationService {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
    suspend fun fetchSystemPrompt(): Result<SystemPromptResponse>
}