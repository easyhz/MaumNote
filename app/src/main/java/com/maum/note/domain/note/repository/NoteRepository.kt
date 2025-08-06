package com.maum.note.domain.note.repository

import androidx.paging.PagingData
import com.maum.note.core.model.note.Note
import com.maum.note.domain.note.model.request.LegacyNoteRequestParam
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteResponse
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(request: NoteRequestParam)
    suspend fun generateNote(param: NoteGenerationRequestParam): Result<NoteResponse>
    fun findAllNotesFlow(): Flow<List<NoteResponse>>

    suspend fun countNotes(): Int

    suspend fun insertLegacyNote(param: LegacyNoteRequestParam)
    fun fetchPagedNotes(): Flow<PagingData<Note>>

    suspend fun deleteNote(noteId: String): Result<Unit>
}