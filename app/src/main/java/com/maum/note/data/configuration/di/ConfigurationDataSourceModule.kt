package com.maum.note.data.configuration.di

import com.maum.note.data.configuration.datasource.local.ConfigurationLocalDataSource
import com.maum.note.data.configuration.datasource.local.ConfigurationLocalDataSourceImpl
import com.maum.note.data.configuration.datasource.remote.ConfigurationRemoteDataSource
import com.maum.note.data.configuration.datasource.remote.ConfigurationRemoteDataSourceImpl
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

    @Binds
    fun bindConfigurationLocalDataSource(
        configurationLocalDataSourceImpl: ConfigurationLocalDataSourceImpl
    ): ConfigurationLocalDataSource
}