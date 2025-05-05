package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
    suspend fun generateNote(request: NoteGenerationRequestParam): Result<NoteGenerationResponse>
}