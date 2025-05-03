package com.maum.note.core.network.gpt.model.error

data class GptError(
    val code: String,
    val message: String,
    val param: String,
    val type: String
)