package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.database.note.entity.NoteWithStudent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteLocalDataSource {
    override suspend fun saveNote(data: NoteEntity) {
        noteDao.upsertNote(note = data)
    }

    override fun findAllNotesFlow(): Flow<List<NoteWithStudent>> {
        return noteDao.findAllNotesFlow()
    }

    override suspend fun insertAndGetNote(noteWithStudent: NoteWithStudent): NoteWithStudent {
        return noteDao.insertAndGetNote(noteWithStudent)
    }

    override suspend fun countNotes(): Int {
        return noteDao.countNotes()
    }
}