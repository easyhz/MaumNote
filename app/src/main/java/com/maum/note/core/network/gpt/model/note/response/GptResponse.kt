package com.maum.note.core.network.gpt.model.note.response

import com.maum.note.core.network.gpt.model.note.response.etc.OutputResponse
import com.maum.note.core.network.gpt.model.note.response.etc.TextResponse
import com.maum.note.core.network.gpt.model.note.response.etc.UsageResponse
import com.squareup.moshi.Json

data class GptResponse(
    @Json(name = "created_at")
    val createdAt: Int,
    val error: Any,
    val id: String,
    val instructions: Any,
    val model: String,
    val `object`: String,
    val output: List<OutputResponse>,
    val status: String,
    val text: TextResponse,
    val usage: UsageResponse
)