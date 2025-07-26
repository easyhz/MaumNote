package com.maum.note.domain.board.usecase.post

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val boardRepository: BoardRepository,
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return boardRepository.deletePost(param)
    }
}