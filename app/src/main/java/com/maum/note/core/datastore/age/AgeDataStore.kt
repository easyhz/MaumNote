package com.maum.note.core.datastore.age

import kotlinx.coroutines.flow.Flow

interface AgeDataStore {
    suspend fun getAge(): Flow<String>
    suspend fun setAge(age: String)
}