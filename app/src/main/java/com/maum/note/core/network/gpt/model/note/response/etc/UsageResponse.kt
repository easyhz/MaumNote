package com.maum.note.core.network.gpt.model.note.response.etc

import com.squareup.moshi.Json

data class UsageResponse(
    @Json(name = "input_tokens")
    val inputTokens: Int,
    @Json(name = "input_tokens_details")
    val inputTokensDetails: InputTokensDetailsResponse,
    @Json(name = "output_tokens")
    val outputTokens: Int,
    @Json(name = "output_tokens_details")
    val outputTokensDetails: OutputTokensDetailsResponse,
    @Json(name = "total_tokens")
    val totalTokens: Int
)