package com.maum.note.domain.tone.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.repository.ToneRepository
import javax.inject.Inject

class UpdateToneUseCase @Inject constructor(
    private val toneRepository: ToneRepository,
): BaseUseCase<UpdateToneRequestParam, Unit>() {
    override suspend fun invoke(param: UpdateToneRequestParam): Result<Unit> {
        return runCatching {
            toneRepository.updateTone(param)
        }
    }
}