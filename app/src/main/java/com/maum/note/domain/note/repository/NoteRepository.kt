package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.CreateNoteRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.CreateNoteResponse

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
    suspend fun generateNote(request: CreateNoteRequestParam): Result<CreateNoteResponse>
}