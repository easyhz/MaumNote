package com.maum.note.data.user.datasource.remote

import com.maum.note.core.supabase.service.user.dto.UserDto
import io.github.jan.supabase.auth.user.UserInfo

interface UserRemoteDataSource {
    fun getCurrentUser(): UserInfo?
    suspend fun fetchUser(userId: String): UserDto?
    suspend fun signInAnonymously()
    suspend fun insertUser(userDto: UserDto)
    suspend fun signOut()
    suspend fun clearUserSession()

    suspend fun updateUserStudentAge(userId: String, ageType: String)
}