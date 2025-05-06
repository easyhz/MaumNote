package com.maum.note.data.configuration.datasource

import com.maum.note.core.firebase.configuration.model.response.ConfigurationResponse

interface ConfigurationRemoteDataSource {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
}