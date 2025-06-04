package com.maum.note.data.user.datasource.remote

import com.maum.note.core.supabase.auth.AuthService
import com.maum.note.core.supabase.user.dto.UserDto
import com.maum.note.core.supabase.user.service.UserService
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
) : UserRemoteDataSource {
    override fun getCurrentUser(): UserInfo? {
        return authService.getCurrentUser()
    }

    override suspend fun fetchUser(userId: String): UserDto? {
        return userService.fetchUser(userId)
    }
    override suspend fun signInAnonymously() {
        authService.signInAnonymously()
    }
    override suspend fun insertUser(userDto: UserDto) {
        return userService.insertUser(userDto)
    }

    override suspend fun signOut() {
        authService.signOut()
    }

    override suspend fun clearUserSession() {
        authService.clearSession()
    }

    override suspend fun updateUserStudentAge(userId: String, ageType: String) {
        return userService.updateUserStudentAge(userId, ageType)
    }
}