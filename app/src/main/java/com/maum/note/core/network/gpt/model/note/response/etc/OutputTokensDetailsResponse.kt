package com.maum.note.core.network.gpt.model.note.response.etc

import com.squareup.moshi.Json

data class OutputTokensDetailsResponse(
    @Json(name = "reasoning_tokens")
    val reasoningTokens: Int
)