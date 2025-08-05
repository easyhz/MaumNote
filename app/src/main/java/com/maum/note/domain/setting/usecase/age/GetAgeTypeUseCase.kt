package com.maum.note.domain.setting.usecase.age

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.model.note.AgeType
import com.maum.note.domain.setting.repository.age.AgeRepository
import javax.inject.Inject

class GetAgeTypeUseCase @Inject constructor(
    private val ageRepository: AgeRepository
): BaseUseCase<Unit, AgeType>() {
    override suspend fun invoke(param: Unit): Result<AgeType> {
        return runCatching {
            val age = ageRepository.getAgeSetting() ?: AgeType.MIXED.name
            AgeType.getByValue(age) ?: AgeType.MIXED
        }
    }
}