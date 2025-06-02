package com.maum.note.data.configuration.datasource.local

import kotlinx.coroutines.flow.Flow

interface ConfigurationLocalDataSource {
    fun getNotificationStatus(): Flow<Boolean>
    fun getNotificationDate(): Flow<String?>

    suspend fun updateNotificationStatus(newValue: Boolean)
    suspend fun updateNotificationDate(newValue: String)
}