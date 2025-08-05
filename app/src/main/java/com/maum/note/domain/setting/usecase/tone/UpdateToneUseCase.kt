package com.maum.note.domain.setting.usecase.tone

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import com.maum.note.domain.setting.model.tone.request.UpdateToneRequestParam
import com.maum.note.domain.setting.repository.tone.ToneRepository
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateToneUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val toneRepository: ToneRepository,
): BaseUseCase<UpdateToneRequestParam, Unit>() {
    override suspend fun invoke(param: UpdateToneRequestParam): Result<Unit> {
        return runCatching {
            val id = userRepository.getCurrentUser()?.id ?: throw AppError.NoUserDataError
            val insertParam = InsertToneRequestParam(
                id = param.toneId,
                userId = id,
                common = param.content,
                playContext = "",
                announcementContent = "",
                letterGreeting = ""
            )
            toneRepository.updateTone(insertParam)
        }
    }
}