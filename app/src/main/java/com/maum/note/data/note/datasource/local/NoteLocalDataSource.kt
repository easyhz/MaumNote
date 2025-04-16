package com.maum.note.data.note.datasource.local

import com.maum.note.data.note.model.request.NoteRequestData

interface NoteLocalDataSource {
    suspend fun saveNote(data: NoteRequestData)
}