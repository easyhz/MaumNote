package com.maum.note.core.network.gpt.model.note.response.etc

import com.squareup.moshi.Json

data class InputTokensDetailsResponse(
    @Json(name = "cached_tokens")
    val cachedTokens: Int
)