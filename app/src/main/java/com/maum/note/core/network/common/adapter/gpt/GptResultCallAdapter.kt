package com.maum.note.core.network.common.adapter.gpt

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class GptResultCallAdapter(
    private val responseType: Type,
    private val gson: Gson
): CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<Type>): Call<Result<Type>> = GptResultCall(call, gson)
}
