package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.util.version.Version
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class CheckUserStepUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val userRepository: UserRepository,
    private val version: Version = Version(),
): BaseUseCase<Unit, UserStep>() {
    override suspend fun invoke(param: Unit): Result<UserStep> {
        return runCatching {
            val config = configurationRepository.fetchConfiguration().getOrThrow()
            checkConfiguration(config) ?: checkIfLoginFlow()
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

    private suspend fun checkIfLoginFlow(): UserStep {
        if (userRepository.isLogin().getOrThrow()) return UserStep.AlreadyLoginToMain

        val userId = userRepository.signInAnonymously().getOrThrow()
        val user = userRepository.getUser(userId).getOrThrow()

        return if (user != null) {
            UserStep.ExistingUserToOnboarding
        } else {
            userRepository.saveUser(SaveUserRequestParam(userId)).getOrThrow()
            UserStep.NewUserToOnboarding
        }
    }


}