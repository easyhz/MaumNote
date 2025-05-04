package com.maum.note.core.network.gpt.di

import com.maum.note.core.network.gpt.api.GptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideGptApiService(@GptRetrofit retrofit: Retrofit): GptApi =
        retrofit.create(GptApi::class.java)
}