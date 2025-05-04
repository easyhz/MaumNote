package com.maum.note.core.network.gpt.model.note.request

import com.maum.note.core.network.gpt.model.note.request.etc.InputRequest

data class GptRequest(
    val input: List<InputRequest>,
    val model: String
)