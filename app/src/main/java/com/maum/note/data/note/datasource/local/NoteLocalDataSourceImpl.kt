package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.note.entity.NoteEntity
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteLocalDataSource {
    override suspend fun saveNote(data: NoteEntity) {
        noteDao.upsertNote(note = data)
    }
}