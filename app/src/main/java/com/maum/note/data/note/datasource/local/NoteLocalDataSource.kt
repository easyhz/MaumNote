package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.database.note.entity.NoteWithStudent
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    suspend fun saveNote(data: NoteEntity)
    fun findAllNotesFlow(): Flow<List<NoteWithStudent>>

    suspend fun insertAndGetNote(noteWithStudent: NoteWithStudent): NoteWithStudent
}