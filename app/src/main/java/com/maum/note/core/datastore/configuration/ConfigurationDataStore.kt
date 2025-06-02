package com.maum.note.core.datastore.configuration

import kotlinx.coroutines.flow.Flow

interface ConfigurationDataStore {
    fun getNotificationStatus(): Flow<Boolean>
    fun getNotificationDate(): Flow<String?>

    suspend fun updateNotificationStatus(newValue: Boolean)
    suspend fun updateNotificationDate(newValue: String)
}