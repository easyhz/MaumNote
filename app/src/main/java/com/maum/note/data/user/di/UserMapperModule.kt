package com.maum.note.data.user.di

import com.maum.note.data.user.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserMapperModule {
    @Provides
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
}