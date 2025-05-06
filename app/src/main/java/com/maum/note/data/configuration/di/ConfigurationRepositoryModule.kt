package com.maum.note.data.configuration.di

import com.maum.note.data.configuration.repository.ConfigurationRepositoryImpl
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConfigurationRepositoryModule {
    @Binds
    fun bindConfigurationRepository(
        configurationRepositoryImpl: ConfigurationRepositoryImpl
    ): ConfigurationRepository
}