package com.maum.note.data.setting.di

import com.maum.note.data.setting.mapper.tone.ToneMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SettingMapperModule {
    @Provides
    fun provideToneMapper(): ToneMapper {
        return ToneMapper()
    }
}