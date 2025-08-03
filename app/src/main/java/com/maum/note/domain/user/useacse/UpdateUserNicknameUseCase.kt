package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateUserNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return userRepository.updateUserNickname(param)
    }
}