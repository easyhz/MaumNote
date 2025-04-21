package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class CheckUserStepUseCase @Inject constructor(
    private val userRepository: UserRepository,
): BaseUseCase<Unit, UserStep>() {
    override suspend fun invoke(param: Unit): Result<UserStep> {
        return runCatching {
            if (userRepository.isLogin().getOrThrow()) {
                return@runCatching UserStep.AlreadyLoginToMain
            }

            val userId = userRepository.signInAnonymously().getOrThrow()
            val user = userRepository.getUser(userId).getOrThrow()

            if (user != null) {
                return@runCatching UserStep.ExistingUserToOnboarding
            }

            userRepository.saveUser(SaveUserRequestParam(userId)).getOrThrow()
            UserStep.NewUserToOnboarding
        }
    }
}