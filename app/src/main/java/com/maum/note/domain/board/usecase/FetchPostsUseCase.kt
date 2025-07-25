package com.maum.note.domain.board.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.model.board.Post
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<Unit, List<Post>>() {
    override suspend fun invoke(param: Unit): Result<List<Post>> {
        return boardRepository.fetchPosts()
    }
}