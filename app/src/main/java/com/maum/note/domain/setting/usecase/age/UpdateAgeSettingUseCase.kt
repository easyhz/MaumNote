package com.maum.note.domain.setting.usecase.age

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.setting.repository.age.AgeRepository
import javax.inject.Inject

class UpdateAgeSettingUseCase @Inject constructor(
    private val ageRepository: AgeRepository
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return runCatching {
            ageRepository.setAgeSetting(age = param)
        }
    }
}