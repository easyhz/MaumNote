package com.maum.note.data.tone.di

import com.maum.note.data.tone.datasource.local.ToneLocalDataSource
import com.maum.note.data.tone.datasource.local.ToneLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ToneDataSourceModule {
     @Binds
     fun bindToneLocalDataSource(
         toneLocalDataSourceImpl: ToneLocalDataSourceImpl
     ): ToneLocalDataSource
}