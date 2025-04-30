package com.maum.note.data.setting.di

import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingDataSourceModule {
     @Binds
     fun bindToneLocalDataSource(
         toneLocalDataSourceImpl: ToneLocalDataSourceImpl
     ): ToneLocalDataSource
}