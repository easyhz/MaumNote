package com.maum.note.core.network.common.converter.gpt

import com.google.gson.Gson
import com.maum.note.core.network.common.model.error.gpt.GptErrorResponse

class GptErrorResponseConverter(
    private val gson: Gson
) {
    fun fromJsonToErrorResponse(json: String): GptErrorResponse {
        return gson.fromJson(json, GptErrorResponse::class.java)
    }
}