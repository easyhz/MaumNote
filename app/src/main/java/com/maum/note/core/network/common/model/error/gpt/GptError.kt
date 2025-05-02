package com.maum.note.core.network.common.model.error.gpt

data class GptError(
    val code: String,
    val message: String,
    val param: String,
    val type: String
)