package com.maum.note.data.note.datasource.remote

import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import com.maum.note.core.supabase.service.note.dto.NoteDto


interface NoteRemoteDataSource {
    suspend fun generateNote(request: GptRequest): Result<GptResponse>
    suspend fun insertNote(noteDto: NoteDto)
}