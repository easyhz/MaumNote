package com.maum.note.data.setting.di

import com.maum.note.data.setting.repository.tone.ToneRepositoryImpl
import com.maum.note.domain.setting.repository.tone.ToneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingRepositoryModule {
     @Binds
     fun bindToneRepository(
         toneRepositoryImpl: ToneRepositoryImpl
     ): ToneRepository
}