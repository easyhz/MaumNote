package com.maum.note.core.datastore.age

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maum.note.core.datastore.config.PreferencesKey
import com.maum.note.core.datastore.di.AgeDataStorePreference
import com.maum.note.core.model.note.AgeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AgeDataStoreImpl @Inject constructor(
    @AgeDataStorePreference private val dataStore: DataStore<Preferences>
): AgeDataStore {
    private val ageKey = stringPreferencesKey(PreferencesKey.AGE)
    override suspend fun getAgeSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[ageKey] ?: AgeType.MIXED.name
        }
    }

    override suspend fun setAgeSetting(age: String) {
        dataStore.edit { preferences ->
            preferences[ageKey] = age
        }
    }
}