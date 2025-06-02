package com.maum.note.domain.configuration.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import javax.inject.Inject

class ShouldNotificationPermissionUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Unit, Boolean>() {
    override suspend fun invoke(param: Unit): Result<Boolean> {
        return configurationRepository.shouldNotificationPermission()
    }
}