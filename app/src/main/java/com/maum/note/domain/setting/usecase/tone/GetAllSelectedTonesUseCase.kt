package com.maum.note.domain.setting.usecase.tone

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.setting.model.tone.response.ToneResponseResult
import com.maum.note.domain.setting.repository.tone.ToneRepository
import javax.inject.Inject

class GetAllSelectedTonesUseCase @Inject constructor(
    private val toneRepository: ToneRepository
): BaseUseCase<Unit, List<ToneResponseResult>>() {
    override suspend fun invoke(param: Unit): Result<List<ToneResponseResult>> {
        return runCatching {
            toneRepository.getAllSelectedTones()
        }
    }
}