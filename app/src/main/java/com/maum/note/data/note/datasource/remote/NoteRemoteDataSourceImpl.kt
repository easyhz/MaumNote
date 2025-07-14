package com.maum.note.data.note.datasource.remote

import com.maum.note.core.network.gpt.api.GptApi
import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import com.maum.note.core.supabase.service.note.dto.NoteDto
import com.maum.note.core.supabase.service.note.service.NoteService
import javax.inject.Inject


class NoteRemoteDataSourceImpl @Inject constructor(
    private val gptApi: GptApi,
    private val noteService: NoteService
): NoteRemoteDataSource {
    override suspend fun generateNote(request: GptRequest): Result<GptResponse> {
        return gptApi.requestNoteGenerate(request)
    }

    override suspend fun insertNote(noteDto: NoteDto) {
        return noteService.insertNote(noteDto)
    }
}