package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.note.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteLocalDataSource {
    override suspend fun saveNote(data: NoteEntity) {
        noteDao.upsertNote(note = data)
    }

    override fun findAllNotesFlow(): Flow<List<NoteEntity>> {
        return noteDao.findAllNotesFlow()
    }
}