package com.maum.note.core.datastore.board

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.maum.note.core.datastore.constant.PreferencesKey
import com.maum.note.core.datastore.di.BoardDataStorePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoardDataStoreImpl @Inject constructor(
    @BoardDataStorePreference private val dataStore: DataStore<Preferences>
): BoardDataStore {
    private val isAnonymousKey = booleanPreferencesKey(PreferencesKey.IS_ANONYMOUS)

    override fun getAnonymousSettingFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[isAnonymousKey] == true
        }
    }

    override suspend fun setAnonymousSetting(isAnonymous: Boolean) {
        dataStore.edit { preferences ->
            preferences[isAnonymousKey] = isAnonymous
        }
    }
}