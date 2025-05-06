package com.maum.note.domain.configuration.repository

import com.maum.note.domain.configuration.model.response.ConfigurationResponse

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
}