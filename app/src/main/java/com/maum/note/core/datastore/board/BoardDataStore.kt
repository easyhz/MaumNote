package com.maum.note.core.datastore.board

import kotlinx.coroutines.flow.Flow

interface BoardDataStore {
    fun getAnonymousSettingFlow(): Flow<Boolean>
    suspend fun setAnonymousSetting(isAnonymous: Boolean)
}