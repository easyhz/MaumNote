package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun invoke(param: Unit): Result<Unit> = runCatching {
        userRepository.signOut()
    }
}