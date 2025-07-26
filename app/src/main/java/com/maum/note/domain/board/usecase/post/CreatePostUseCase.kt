package com.maum.note.domain.board.usecase.post

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<CreatePostRequest, Unit>() {
    override suspend fun invoke(param: CreatePostRequest): Result<Unit> {
        return boardRepository.createPost(param)
    }
}