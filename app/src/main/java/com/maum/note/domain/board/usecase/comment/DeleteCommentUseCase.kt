package com.maum.note.domain.board.usecase.comment

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return boardRepository.deleteComment(param)
    }
}