package com.maum.note.domain.configuration.repository

import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ConfigurationResponse>
    suspend fun shouldNotificationPermission(): Result<Boolean>
    suspend fun updateNotificationPermission(isAllowed: Boolean): Result<Unit>
    fun getIsSynchronization(): Flow<Boolean>
    suspend fun updateIsSynchronization(newValue: Boolean)
}