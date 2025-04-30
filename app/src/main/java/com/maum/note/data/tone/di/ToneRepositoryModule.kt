package com.maum.note.data.tone.di

import com.maum.note.data.tone.repository.ToneRepositoryImpl
import com.maum.note.domain.tone.repository.ToneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ToneRepositoryModule {
     @Binds
     fun bindToneRepository(
         toneRepositoryImpl: ToneRepositoryImpl
     ): ToneRepository
}