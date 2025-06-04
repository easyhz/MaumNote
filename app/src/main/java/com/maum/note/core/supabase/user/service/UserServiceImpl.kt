package com.maum.note.core.supabase.user.service

import com.maum.note.core.supabase.user.dto.UserDto
import com.maum.note.core.supabase.constant.Table
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val postgrest: Postgrest
): UserService {
    override suspend fun insertUser(userDto: UserDto) {
        postgrest.from(Table.USERS.name).insert(userDto)
    }

    override suspend fun fetchUser(userId: String): UserDto? {
        return postgrest.from(Table.USERS.name).select {
            filter {
                eq(Table.USERS.ID, userId)
            }
        }.decodeSingle()
    }
}