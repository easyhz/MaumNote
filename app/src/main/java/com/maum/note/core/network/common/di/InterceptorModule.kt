package com.maum.note.core.network.common.di

import com.maum.note.core.network.interceptor.gpt.GptAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InterceptorModule {
    @Provides
    @Singleton
    @HttpLoggingLevel
    fun provideHttpLoggingLevel(
        @Debug debug: Boolean
    ): HttpLoggingInterceptor.Level =
        if (debug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
        @HttpLoggingLevel level: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(level)
    }

    @GptClient
    @Provides
    fun provideGptOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: GptAuthInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

}