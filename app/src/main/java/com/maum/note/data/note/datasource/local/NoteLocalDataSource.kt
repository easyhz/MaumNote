package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteLocalDataSource {
    suspend fun saveNote(data: NoteEntity)
    fun findAllNotesFlow(): Flow<List<NoteEntity>>

    suspend fun insertAndGetNote(note: NoteEntity): NoteEntity
}