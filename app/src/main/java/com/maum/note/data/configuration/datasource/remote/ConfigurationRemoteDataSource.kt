package com.maum.note.data.configuration.datasource.remote

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
import com.maum.note.core.firebase.configuration.model.response.SystemPromptResponse

interface ConfigurationRemoteDataSource {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
    suspend fun fetchSystemPrompt(): Result<SystemPromptResponse>
}