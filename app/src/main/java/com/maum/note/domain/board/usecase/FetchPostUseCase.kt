package com.maum.note.domain.board.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.model.board.Post
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class FetchPostUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<String, Post>() {
    override suspend fun invoke(param: String): Result<Post> {
        return boardRepository.fetchPost(param)
    }
}