package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.NoteRequest

interface NoteRepository {
    suspend fun saveNote(request: NoteRequest)
}