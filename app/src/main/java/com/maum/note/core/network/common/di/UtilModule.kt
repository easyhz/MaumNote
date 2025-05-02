package com.maum.note.core.network.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maum.note.BuildConfig
import com.maum.note.core.network.common.adapter.gpt.GptResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {
    @Provides
    @Singleton
    fun provideResultCallAdapterFactory(gson: Gson): GptResultCallAdapterFactory =
        GptResultCallAdapterFactory(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    @Debug
    fun provideIsDebugEnabled(): Boolean = BuildConfig.DEBUG
}