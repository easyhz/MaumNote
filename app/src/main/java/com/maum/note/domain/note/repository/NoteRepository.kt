package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import com.maum.note.domain.note.model.response.NoteResponse
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
    suspend fun generateNote(param: NoteGenerationRequestParam): Result<NoteGenerationResponse>
    fun findAllNotesFlow(): Flow<List<NoteResponse>>
}