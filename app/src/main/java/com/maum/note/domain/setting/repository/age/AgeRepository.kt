package com.maum.note.domain.setting.repository.age

import kotlinx.coroutines.flow.Flow

interface AgeRepository {
    suspend fun getAgeSetting(): Flow<String>
    suspend fun setAgeSetting(age: String)
}