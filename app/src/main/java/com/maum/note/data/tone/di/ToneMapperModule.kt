package com.maum.note.data.tone.di

import com.maum.note.data.tone.mapper.ToneMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ToneMapperModule {
    @Provides
    fun provideToneMapper(): ToneMapper {
        return ToneMapper()
    }
}