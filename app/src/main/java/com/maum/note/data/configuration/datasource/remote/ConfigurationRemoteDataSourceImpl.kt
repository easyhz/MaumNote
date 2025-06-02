package com.maum.note.data.configuration.datasource.remote

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse
import com.maum.note.core.firebase.configuration.model.response.SystemPromptResponse
import com.maum.note.core.firebase.configuration.service.ConfigurationService
import javax.inject.Inject

class ConfigurationRemoteDataSourceImpl @Inject constructor(
    private val configurationService: ConfigurationService
): ConfigurationRemoteDataSource {
    override suspend fun fetchConfiguration(): Result<ConfigurationResponse> {
        return configurationService.fetchConfiguration()
    }

    override suspend fun fetchSystemPrompt(): Result<SystemPromptResponse> {
        return configurationService.fetchSystemPrompt()
    }
}