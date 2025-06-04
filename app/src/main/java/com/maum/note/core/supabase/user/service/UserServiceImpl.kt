package com.maum.note.core.supabase.user.service

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.user.dto.UserDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : UserService {
    override suspend fun insertUser(userDto: UserDto) {
        postgrest.from(Table.USERS.name).insert(userDto)
    }

    override suspend fun fetchUser(userId: String): UserDto? {
        return postgrest.from(Table.USERS.name).select {
            filter {
                eq(Table.USERS.ID, userId)
            }
        }.decodeSingleOrNull()
    }

    override suspend fun updateUserNickname(userId: String, nickname: String) {
        postgrest.from(Table.USERS.name).update(
            {
                set(Table.USERS.NICKNAME, nickname)
            }
        ) {
            filter {
                eq(Table.USERS.ID, userId)
            }
        }
    }

    override suspend fun updateUserStudentAge(userId: String, ageType: String) {
        postgrest.from(Table.USERS.name).update(
            {
                set(Table.USERS.STUDENT_AGE, ageType)
            }
        ) {
            filter {
                eq(Table.USERS.ID, userId)
            }
        }
    }

    override suspend fun updateUserHasAgreedToTerms(
        userId: String,
        hasAgreedToTerms: Boolean
    ) {
        postgrest.from(Table.USERS.name).update(
            {
                set(Table.USERS.HAS_AGREED_TO_TERMS, hasAgreedToTerms)
            }
        ) {
            filter {
                eq(Table.USERS.ID, userId)
            }
        }
    }
}