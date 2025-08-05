package com.maum.note.domain.setting.usecase.tone

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import com.maum.note.domain.setting.model.tone.request.UpdateAllToneRequestParam
import com.maum.note.domain.setting.repository.tone.ToneRepository
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateAllToneUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val toneRepository: ToneRepository,
): BaseUseCase<UpdateAllToneRequestParam, Unit>() {
    override suspend fun invoke(param: UpdateAllToneRequestParam): Result<Unit> {
        return runCatching {
            val id = userRepository.getCurrentUser()?.id ?: throw AppError.NoUserDataError
            val insertParam = InsertToneRequestParam(
                id = param.toneId,
                userId = id,
                common = param.common,
                letterGreeting = param.letterGreeting,
                playContext = param.playContext,
                announcementContent = param.announcementContent
            )
            toneRepository.updateTone(insertParam)
        }
    }
}