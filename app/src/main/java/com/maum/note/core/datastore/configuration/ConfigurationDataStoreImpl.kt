package com.maum.note.core.datastore.configuration

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maum.note.core.datastore.constant.PreferencesKey
import com.maum.note.core.datastore.di.ConfigurationDataStorePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConfigurationDataStoreImpl @Inject constructor(
    @ConfigurationDataStorePreference private val dataStore: DataStore<Preferences>
): ConfigurationDataStore {
    private val notificationStatusKey = booleanPreferencesKey(PreferencesKey.NOTIFICATION_STATUS)
    private val notificationDateKey = stringPreferencesKey(PreferencesKey.NOTIFICATION_DATE)

    override fun getNotificationStatus(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[notificationStatusKey] == true
        }
    }

    override fun getNotificationDate(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[notificationDateKey]
        }
    }

    override suspend fun updateNotificationStatus(newValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[notificationStatusKey] = newValue
        }
    }

    override suspend fun updateNotificationDate(newValue: String) {
        dataStore.edit { preferences ->
            preferences[notificationDateKey] = newValue
        }
    }
}