package com.maum.note.core.supabase.user.service

import com.maum.note.core.supabase.user.dto.UserDto

interface UserService {
    suspend fun insertUser(userDto: UserDto)
    suspend fun fetchUser(userId: String): UserDto?

    suspend fun updateUserNickname(userId: String, nickname: String)
    suspend fun updateUserStudentAge(userId: String, ageType: String)
    suspend fun updateUserHasAgreedToTerms(userId: String, hasAgreedToTerms: Boolean)
}