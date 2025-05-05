package com.maum.note.core.common.di

import com.maum.note.core.common.error.ErrorHandler
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.helper.serializable.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).add(LocalDateTimeAdapter()).build()

    @Provides
    @Singleton
    fun provideErrorHandler(
        resourceHelper: ResourceHelper,
    ): ErrorHandler =
        ErrorHandler(resourceHelper = resourceHelper)
}