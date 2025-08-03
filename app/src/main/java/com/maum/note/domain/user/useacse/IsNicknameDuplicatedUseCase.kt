package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class IsNicknameDuplicatedUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<String, Boolean>() {
    override suspend fun invoke(param: String): Result<Boolean> {
        return userRepository.isNicknameDuplicated(param)
    }
}