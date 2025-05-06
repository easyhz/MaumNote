package com.maum.note.core.common.di

import com.maum.note.core.common.error.ErrorHandler
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.helper.serializable.LocalDateTimeAdapter
import com.maum.note.core.common.util.validation.ValidationInput
import com.maum.note.core.common.util.version.Version
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


    @Provides
    fun provideVersion(): Version {
        return Version()
    }

    @Provides
    fun provideValidationInput(): ValidationInput {
        return ValidationInput()
    }
}