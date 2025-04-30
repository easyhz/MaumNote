package com.maum.note.data.setting.datasource.age.local

import kotlinx.coroutines.flow.Flow

interface AgeLocalDataSource {
    suspend fun getAgeSetting(): Flow<String>
    suspend fun setAgeSetting(age: String)
}