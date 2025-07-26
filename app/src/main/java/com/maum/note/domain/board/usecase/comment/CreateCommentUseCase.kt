package com.maum.note.domain.board.usecase.comment

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<CreateCommentRequest, Unit>() {
    override suspend fun invoke(param: CreateCommentRequest): Result<Unit> {
        return boardRepository.createComment(param)
    }
}