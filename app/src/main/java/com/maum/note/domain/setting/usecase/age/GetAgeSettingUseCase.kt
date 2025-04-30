package com.maum.note.domain.setting.usecase.age

import com.maum.note.domain.setting.repository.age.AgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAgeSettingUseCase @Inject constructor(
    private val ageRepository: AgeRepository
) {
    suspend fun invoke(): Flow<String> {
        return ageRepository.getAgeSetting()
    }
}