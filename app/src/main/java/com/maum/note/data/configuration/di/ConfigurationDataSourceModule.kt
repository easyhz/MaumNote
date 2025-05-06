package com.maum.note.data.configuration.di

import com.maum.note.data.configuration.datasource.ConfigurationRemoteDataSource
import com.maum.note.data.configuration.datasource.ConfigurationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConfigurationDataSourceModule {
    @Binds
    fun bindConfigurationRemoteDataSource(
        configurationRemoteDataSourceImpl: ConfigurationRemoteDataSourceImpl
    ): ConfigurationRemoteDataSource
}