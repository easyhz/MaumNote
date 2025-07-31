package com.maum.note.domain.user.useacse

import com.maum.note.domain.user.model.response.AuthUser
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun invoke(): AuthUser? {
        return userRepository.getCurrentUser()
    }
}