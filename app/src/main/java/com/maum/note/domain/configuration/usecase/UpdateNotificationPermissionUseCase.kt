package com.maum.note.domain.configuration.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import javax.inject.Inject

class UpdateNotificationPermissionUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): BaseUseCase<Boolean, Unit>() {
    override suspend fun invoke(param: Boolean): Result<Unit> {
        return configurationRepository.updateNotificationPermission(param)
    }
}