package com.maum.note.data.user.repository

import android.util.Log
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.data.user.mapper.UserMapper
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.request.UpdateUserStudentRequestParam
import com.maum.note.domain.user.model.response.AuthUser
import com.maum.note.domain.user.model.response.User
import com.maum.note.domain.user.repository.UserRepository
import io.github.jan.supabase.postgrest.exception.PostgrestRestException
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper,
) : UserRepository {
    override fun isLogin(): Result<Boolean> {
        return runCatching {
            userRemoteDataSource.getCurrentUser() != null
        }
    }

    override fun getCurrentUser(): AuthUser? {
        val userInfo = userRemoteDataSource.getCurrentUser()
        return userMapper.mapUserInfoToAuthUser(userInfo)
    }

    override suspend fun fetchUser(uid: String): User? {
        return userRemoteDataSource.fetchUser(uid)?.let { userMapper.mapUserDtoToUser(it) }
    }

    override suspend fun signInAnonymously() {
        return userRemoteDataSource.signInAnonymously()
    }

    override suspend fun saveUser(saveUserRequestParam: SaveUserRequestParam): Result<Unit> {
        val maxRetries = 5
        var currentAttempt = 0
        var length = 4

        return runCatching {
            while (true) {
                try {
                    userRemoteDataSource.insertUser(
                        userDto = userMapper.mapToUserDto(saveUserRequestParam, length)
                    )
                    return@runCatching
                } catch (e: PostgrestRestException) {
                    if (isDuplicateKeyError(e)) {
                        currentAttempt++
                        if (currentAttempt >= maxRetries) throw e

                        length = if (currentAttempt >= 3) 5 else 4

                        Log.w("saveUser", "중복키 에러 발생. 재시도 $currentAttempt/$maxRetries")
                        delay(100L)
                    } else {
                        throw e
                    }
                }
            }
        }
    }

    override suspend fun signOut() {
        userRemoteDataSource.signOut()
    }

    override suspend fun clearUserSession() {
        userRemoteDataSource.clearUserSession()
    }

    override suspend fun updateUserStudentAge(updateUserStudentRequestParam: UpdateUserStudentRequestParam) {
        userRemoteDataSource.updateUserStudentAge(userId = updateUserStudentRequestParam.userId, ageType = updateUserStudentRequestParam.ageType)
    }

    private fun isDuplicateKeyError(e: PostgrestRestException): Boolean {
        return e.message?.contains("duplicate key value violates unique constraint") == true
    }
}