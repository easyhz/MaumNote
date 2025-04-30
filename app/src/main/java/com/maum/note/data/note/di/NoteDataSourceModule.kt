package com.maum.note.data.note.di

import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.datasource.local.NoteLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NoteDataSourceModule {
    @Binds
    fun bindNoteLocalDataSource(
        noteLocalDataSourceImpl: NoteLocalDataSourceImpl
    ): NoteLocalDataSource
}