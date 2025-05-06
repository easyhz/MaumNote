package com.maum.note.domain.configuration.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.repository.ConfigurationRepository
import javax.inject.Inject

class FetchConfigurationUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
): BaseUseCase<Unit, ConfigurationResponse>() {
    override suspend fun invoke(param: Unit): Result<ConfigurationResponse> {
        return configurationRepository.fetchConfiguration()
    }
}