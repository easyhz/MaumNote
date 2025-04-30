package com.maum.note.domain.setting.usecase.tone

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.setting.model.tone.request.UpdateToneRequestParam
import com.maum.note.domain.setting.repository.tone.ToneRepository
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