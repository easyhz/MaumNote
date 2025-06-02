package com.maum.note.domain.configuration.repository

import com.maum.note.domain.configuration.model.response.ConfigurationResponse

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
    suspend fun shouldNotificationPermission(): Result<Boolean>
    suspend fun updateNotificationPermission(isAllowed: Boolean): Result<Unit>
}