package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.data.note.mapper.toEntity
import com.maum.note.data.note.model.request.NoteRequestData
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteLocalDataSource {
    override suspend fun saveNote(data: NoteRequestData) {
        noteDao.upsertNote(note = data.toEntity())
    }
}