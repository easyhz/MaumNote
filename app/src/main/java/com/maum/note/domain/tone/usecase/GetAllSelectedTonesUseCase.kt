package com.maum.note.domain.tone.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.tone.model.response.ToneResponseResult
import com.maum.note.domain.tone.repository.ToneRepository
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