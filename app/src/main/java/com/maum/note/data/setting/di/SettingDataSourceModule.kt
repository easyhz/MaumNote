package com.maum.note.data.setting.di

import com.maum.note.data.setting.datasource.age.local.AgeLocalDataSource
import com.maum.note.data.setting.datasource.age.local.AgeLocalDataSourceImpl
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSourceImpl
import com.maum.note.data.setting.datasource.tone.remote.ToneRemoteDataSource
import com.maum.note.data.setting.datasource.tone.remote.ToneRemoteDataSourceImpl
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

     @Binds
     fun bindAgeLocalDataSource(
         ageLocalDataSourceImpl: AgeLocalDataSourceImpl
     ): AgeLocalDataSource

     @Binds
     fun bindToneRemoteDataSource(
         toneRemoteDataSourceImpl: ToneRemoteDataSourceImpl
     ): ToneRemoteDataSource
}