package com.maum.note.core.network.interceptor.gpt

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GptAuthInterceptor @Inject constructor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val accessToken = ""

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(newRequest)
    }
}