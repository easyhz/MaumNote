package com.maum.note.domain.board.usecase.post

import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class FetchPagesPostsUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    operator fun invoke() = boardRepository.fetchPagedPosts()
}