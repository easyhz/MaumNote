package com.maum.note.domain.board.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class SetAnonymousSettingUseCase @Inject constructor(
    private val boardRepository: BoardRepository
): BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(param: Boolean): Result<Unit> {
        return boardRepository.setAnonymousSetting(param)
    }
}