package com.maum.note.data.note.datasource.local

import com.maum.note.core.database.note.entity.NoteEntity

interface NoteLocalDataSource {
    suspend fun saveNote(data: NoteEntity)
}