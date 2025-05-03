package com.maum.note.core.network.gpt.converter

import com.google.gson.Gson
import com.maum.note.core.network.gpt.model.error.GptErrorResponse

class GptErrorResponseConverter(
    private val gson: Gson
) {
    fun fromJsonToErrorResponse(json: String): GptErrorResponse {
        return gson.fromJson(json, GptErrorResponse::class.java)
    }
}