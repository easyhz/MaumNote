package com.maum.note.data.setting.datasource.age.local

interface AgeLocalDataSource {
    suspend fun getAgeSetting(): String?
    suspend fun setAgeSetting(age: String)
}