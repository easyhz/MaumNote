package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.NoteRequestParam

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
}