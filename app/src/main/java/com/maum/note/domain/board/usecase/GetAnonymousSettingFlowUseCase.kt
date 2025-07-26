package com.maum.note.domain.board.usecase

import com.maum.note.domain.board.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnonymousSettingFlowUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    fun invoke(): Flow<Boolean> {
        return boardRepository.getAnonymousSettingFlow()
    }
}