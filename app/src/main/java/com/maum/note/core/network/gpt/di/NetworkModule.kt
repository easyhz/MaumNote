package com.maum.note.core.network.gpt.di

import com.maum.note.core.network.gpt.adapter.GptResultCallAdapterFactory
import com.google.gson.Gson
import com.maum.note.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @GptRetrofit
    @Singleton
    @Provides
    fun provideGptClient(
        @GptClient client: OkHttpClient,
        gptResultCallAdapterFactory: GptResultCallAdapterFactory,
        gson: Gson,
    ): Retrofit = Retrofit.Builder().apply {
        client(client)
        baseUrl(BuildConfig.GPT_API_URL)
        addConverterFactory(GsonConverterFactory.create(gson))
        addCallAdapterFactory(gptResultCallAdapterFactory)
    }.build()

}