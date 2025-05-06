package com.maum.note.data.configuration.repository

import com.maum.note.data.configuration.datasource.ConfigurationRemoteDataSource
import com.maum.note.data.configuration.mapper.ConfigurationMapper
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationMapper: ConfigurationMapper,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource
): ConfigurationRepository {
    override suspend fun fetchConfiguration(): Result<ConfigurationResponse> {
        return configurationRemoteDataSource.fetchConfiguration().map { configurationMapper.mapToResponse(it) }
    }
}