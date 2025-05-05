package com.maum.note.core.network.gpt.api

import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApi {
    @POST("/v1/responses")
    suspend fun requestNoteGenerate(@Body request: GptRequest): Result<GptResponse>
}