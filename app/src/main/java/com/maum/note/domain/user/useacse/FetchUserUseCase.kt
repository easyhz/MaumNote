package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.domain.user.model.response.User
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<Unit, User>() {
    override suspend fun invoke(param: Unit): Result<User>  = runCatching {
        val userId = userRepository.getCurrentUser()?.id ?: throw AppError.NoUserDataError
        userRepository.fetchUser(userId) ?: throw AppError.NoUserDataError
    }
}