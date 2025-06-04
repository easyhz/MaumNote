package com.maum.note.domain.user.useacse

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.core.common.error.AppError
import com.maum.note.domain.user.model.request.UpdateUserStudentRequestParam
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateUserStudentAgeUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> = runCatching {
        val user = userRepository.getCurrentUser() ?: throw AppError.NoUserDataError
        val updateUserStudentRequestParam = getUpdateUserStudentRequestParam(userId = user.id, ageType = param)
        userRepository.updateUserStudentAge(updateUserStudentRequestParam)
    }

    private fun getUpdateUserStudentRequestParam(userId: String, ageType: String) = UpdateUserStudentRequestParam(
        userId = userId,
        ageType = ageType
    )
}