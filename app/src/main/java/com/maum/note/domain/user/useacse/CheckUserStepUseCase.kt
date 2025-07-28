package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.util.version.Version
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckUserStepUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val userRepository: UserRepository,
    private val version: Version = Version(),
): BaseUseCase<Unit, UserStep>() {
    override suspend fun invoke(param: Unit): Result<UserStep> {
        return runCatching {
            val config = configurationRepository.fetchConfiguration().getOrThrow()
            checkConfiguration(config) ?: checkLoginFlow()
        }
    }

    private fun checkConfiguration(configuration: ConfigurationResponse): UserStep? {
        return when {
            configuration.maintenanceNotice.isNotBlank() ->
                UserStep.Maintenance(configuration.maintenanceNotice)

            version.needsUpdate(configuration.androidVersion) ->
                UserStep.Update(configuration.androidVersion)

            else -> null
        }
    }

    private suspend fun checkLoginFlow(): UserStep {
        if (userRepository.isLogin().getOrThrow()) return UserStep.AlreadyLoginToMain

        userRepository.signInAnonymously()
        val currentUser = userRepository.getCurrentUser() ?: throw AppError.NoUserDataError

        val existingUser = userRepository.fetchUser(currentUser.id)
        if (existingUser != null) {
            return UserStep.ExistingUserToOnboarding
        }

        return handleNewUser(currentUser.id)
    }

    private suspend fun handleNewUser(userId: String): UserStep {
        userRepository.saveUser(SaveUserRequestParam(userId)).getOrThrow()
        val isSynchronization = configurationRepository.getIsSynchronization().first()
        return if (!isSynchronization) {
            UserStep.NeedSynchronize
        } else {
            UserStep.NewUserToOnboarding
        }
    }

}