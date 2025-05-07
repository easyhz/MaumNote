package com.maum.note.data.user.di

import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.data.user.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserMapperModule {
    @Provides
    fun provideUserMapper(
        appDateTimeFormatter: AppDateTimeFormatter,
    ): UserMapper {
        return UserMapper(appDateTimeFormatter)
    }
}