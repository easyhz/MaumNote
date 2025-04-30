package com.maum.note.data.tone.datasource.local

import com.maum.note.core.database.tone.dao.ToneDao
import com.maum.note.core.database.tone.entity.ToneEntity
import javax.inject.Inject

class ToneLocalDataSourceImpl @Inject constructor(
    private val toneDao: ToneDao
): ToneLocalDataSource {
    override suspend fun findAllSelectedTones(): List<ToneEntity> {
        return toneDao.findAllSelectedTones()
    }

    override suspend fun findByNoteType(noteType: String): ToneEntity? {
        return toneDao.findByNoteType(noteType)
    }

    override suspend fun updateTone(noteType: String, content: String) {
        toneDao.updateTone(noteType, content)
    }
}