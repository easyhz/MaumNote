package com.maum.note.domain.setting.usecase.tone

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.setting.model.tone.response.ToneResponse
import com.maum.note.domain.setting.repository.tone.ToneRepository
import javax.inject.Inject

class FetchToneUseCase @Inject constructor(
    private val toneRepository: ToneRepository
): BaseUseCase<Unit, ToneResponse>() {
    override suspend fun invoke(param: Unit): Result<ToneResponse> {
        return toneRepository.fetchTone()
    }
}