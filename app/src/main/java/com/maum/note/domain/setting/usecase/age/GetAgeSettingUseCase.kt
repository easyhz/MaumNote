package com.maum.note.domain.setting.usecase.age

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.setting.repository.age.AgeRepository
import javax.inject.Inject

class GetAgeSettingUseCase @Inject constructor(
    private val ageRepository: AgeRepository
): BaseUseCase<Unit, String>() {
    override suspend fun invoke(param: Unit): Result<String> {
        return runCatching {
            ageRepository.getAgeSetting()
        }
    }
}