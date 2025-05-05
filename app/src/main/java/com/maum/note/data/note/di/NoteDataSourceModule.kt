package com.maum.note.data.note.di

import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.datasource.local.NoteLocalDataSourceImpl
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSource
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSourceImpl
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

    @Binds
    fun bindNoteRemoteDataSource(
        noteRemoteDataSourceImpl: NoteRemoteDataSourceImpl
    ): NoteRemoteDataSource
}