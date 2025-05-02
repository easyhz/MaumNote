package com.maum.note.core.network.common.util.gpt

import com.google.gson.Gson
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.error.getGptError
import com.maum.note.core.network.common.converter.gpt.GptErrorResponseConverter
import retrofit2.Response

fun <T> Response<T>.toGptResult(
    gson: Gson
): Result<T> {
    val body = this.body()
    val errorBody = this.errorBody()?.string()
    if (this.isSuccessful) {
        return if (body != null) {
            Result.success(body)
        } else {
            Result.failure(AppError.UnexpectedError)
        }
    }
    if (errorBody == null) {
        return Result.failure(AppError.UnexpectedError)
    }
    try {
        val errorResponse = GptErrorResponseConverter(gson).fromJsonToErrorResponse(errorBody)

        val httpError = getGptError(error = errorResponse)

        return Result.failure(httpError)
    } catch (e: Exception) {
        return  Result.failure(AppError.UnexpectedError)
    }
}