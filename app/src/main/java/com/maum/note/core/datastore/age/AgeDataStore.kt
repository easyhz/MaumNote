package com.maum.note.core.datastore.age

import kotlinx.coroutines.flow.Flow

interface AgeDataStore {
    suspend fun getAgeSetting(): Flow<String>
    suspend fun setAgeSetting(age: String)
}