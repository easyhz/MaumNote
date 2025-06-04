package com.maum.note.data.user.datasource.remote

import com.maum.note.core.supabase.user.dto.UserDto
import io.github.jan.supabase.auth.user.UserInfo

interface UserRemoteDataSource {
    fun getCurrentUser(): UserInfo?
    suspend fun fetchUser(userId: String): UserDto?
    suspend fun signInAnonymously()
    suspend fun insertUser(userDto: UserDto)
}