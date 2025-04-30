package com.maum.note.domain.setting.repository.age

interface AgeRepository {
    suspend fun getAgeSetting(): String
    suspend fun setAgeSetting(age: String)
}