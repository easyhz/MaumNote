package com.maum.note.data.note.datasource.remote

import com.maum.note.core.network.gpt.api.GptApi
import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import javax.inject.Inject


class NoteRemoteDataSourceImpl @Inject constructor(
    private val gptApi: GptApi
): NoteRemoteDataSource {
    override suspend fun generateNote(request: GptRequest): Result<GptResponse> {
        return gptApi.requestNoteGenerate(request)
    }
}