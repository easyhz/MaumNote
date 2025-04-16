package com.maum.note.data.note.di

import com.maum.note.data.note.repository.NoteRepositoryImpl
import com.maum.note.domain.note.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
     @Binds
     fun bindNoteRepository(
         noteRepositoryImpl: NoteRepositoryImpl
     ): NoteRepository
}