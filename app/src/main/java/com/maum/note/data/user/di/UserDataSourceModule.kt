package com.maum.note.data.user.di

import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.data.user.datasource.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserDataSourceModule {
    @Binds
    fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

}