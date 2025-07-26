package com.maum.note.domain.board.usecase.comment

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.model.board.Comment
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class FetchCommentsUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<String, List<Comment>>() {
    override suspend fun invoke(param: String): Result<List<Comment>> {
        return boardRepository.fetchComments(param)
    }
}