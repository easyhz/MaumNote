package com.maum.note.data.board.datasource.local.board

import com.maum.note.core.datastore.board.BoardDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardLocalDataSourceImpl @Inject constructor(
    private val boardDataStore: BoardDataStore
): BoardLocalDataSource {
    override fun getAnonymousSettingFlow(): Flow<Boolean> {
        return boardDataStore.getAnonymousSettingFlow()
    }

    override suspend fun setAnonymousSetting(isAnonymous: Boolean) {
        boardDataStore.setAnonymousSetting(isAnonymous)
    }
}