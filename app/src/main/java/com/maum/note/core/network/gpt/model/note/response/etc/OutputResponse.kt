package com.maum.note.core.network.gpt.model.note.response.etc

data class OutputResponse(
    val content: List<ContentResponse>,
    val id: String,
    val role: String,
    val status: String,
    val type: String
)