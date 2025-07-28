package com.maum.note.data.configuration.datasource.local

import com.maum.note.core.datastore.configuration.ConfigurationDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigurationLocalDataSourceImpl @Inject constructor(
    private val configurationDataStore: ConfigurationDataStore
): ConfigurationLocalDataSource {
    override fun getNotificationStatus(): Flow<Boolean> {
        return configurationDataStore.getNotificationStatus()
    }

    override fun getNotificationDate(): Flow<String?> {
        return configurationDataStore.getNotificationDate()
    }

    override suspend fun updateNotificationStatus(newValue: Boolean) {
        configurationDataStore.updateNotificationStatus(newValue)
    }

    override suspend fun updateNotificationDate(newValue: String) {
        configurationDataStore.updateNotificationDate(newValue)
    }

    override fun getIsSynchronization(): Flow<Boolean> {
        return configurationDataStore.getIsSynchronization()
    }

    override suspend fun updateIsSynchronization(newValue: Boolean) {
        configurationDataStore.updateIsSynchronization(newValue)
    }
}