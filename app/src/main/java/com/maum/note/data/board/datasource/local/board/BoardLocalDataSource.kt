package com.maum.note.data.board.datasource.local.board

import kotlinx.coroutines.flow.Flow

interface BoardLocalDataSource {
    fun getAnonymousSettingFlow(): Flow<Boolean>
    suspend fun setAnonymousSetting(isAnonymous: Boolean)
}