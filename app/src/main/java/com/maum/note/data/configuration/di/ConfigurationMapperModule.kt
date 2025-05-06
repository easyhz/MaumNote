package com.maum.note.data.configuration.di

import com.maum.note.data.configuration.mapper.ConfigurationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationMapperModule {
    @Provides
    fun provideConfigurationMapper(): ConfigurationMapper {
        return ConfigurationMapper()
    }
}