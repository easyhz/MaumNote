package com.maum.note.data.board.di

import com.maum.note.data.board.datasource.remote.post.PostRemoteDataSource
import com.maum.note.data.board.datasource.remote.post.PostRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface BoardDataSourceModule {
    @Binds
    fun bindPostRemoteDataSource(
        postRemoteDataSourceImpl: PostRemoteDataSourceImpl
    ): PostRemoteDataSource
}