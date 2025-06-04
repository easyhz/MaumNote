package com.maum.note.core.supabase.user.service

import com.maum.note.core.supabase.user.dto.UserDto

interface UserService {
    suspend fun insertUser(userDto: UserDto)
    suspend fun fetchUser(userId: String): UserDto?
}