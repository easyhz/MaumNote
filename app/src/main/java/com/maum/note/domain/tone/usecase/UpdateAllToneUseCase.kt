package com.maum.note.domain.tone.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.repository.ToneRepository
import javax.inject.Inject

class UpdateAllToneUseCase @Inject constructor(
    private val toneRepository: ToneRepository,
): BaseUseCase<List<UpdateToneRequestParam>, Unit>() {
    override suspend fun invoke(param: List<UpdateToneRequestParam>): Result<Unit> {
        return runCatching {
            param.forEach { tone ->
                toneRepository.updateTone(tone)
            }
        }
    }
}