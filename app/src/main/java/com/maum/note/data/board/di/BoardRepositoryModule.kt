package com.maum.note.data.board.di

import com.maum.note.data.board.repository.BoardRepositoryImpl
import com.maum.note.domain.board.repository.BoardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BoardRepositoryModule {
    @Binds
    fun bindBoardRepository(boardRepositoryImpl: BoardRepositoryImpl): BoardRepository
}