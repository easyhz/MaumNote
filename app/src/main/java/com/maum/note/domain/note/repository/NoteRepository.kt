package com.maum.note.domain.note.repository

import com.maum.note.domain.note.model.request.LegacyNoteRequestParam
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteResponse
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
    suspend fun generateNote(param: NoteGenerationRequestParam): Result<NoteResponse>
    fun findAllNotesFlow(): Flow<List<NoteResponse>>

    suspend fun countNotes(): Int

    suspend fun insertLegacyNote(param: LegacyNoteRequestParam)
}