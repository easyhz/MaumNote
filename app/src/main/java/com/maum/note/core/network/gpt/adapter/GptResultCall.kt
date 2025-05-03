package com.maum.note.core.network.gpt.adapter

import com.google.gson.Gson
import com.maum.note.core.common.error.AppError
import com.maum.note.core.network.gpt.util.toGptResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


internal class GptResultCall<T>(
    private val delegate: Call<T>,
    private val gson: Gson
): Call<Result<T>> {
    override fun clone(): Call<Result<T>> = GptResultCall(delegate.clone(), gson)

    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException()

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@GptResultCall, Response.success(response.toGptResult(gson)))
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val failure = when(throwable) {
                    is IOException -> AppError.NetworkConnectionError
                    else -> AppError.UnexpectedError
                }
                callback.onResponse(this@GptResultCall, Response.success(Result.failure(failure)))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
