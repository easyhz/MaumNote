package com.maum.note.domain.user.repository

import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.response.UserResponseResult

interface UserRepository {
    fun isLogin(): Result<Boolean>
    fun getUserId(): Result<String>
    suspend fun getUser(uid: String): Result<UserResponseResult?>
    suspend fun signInAnonymously(): Result<String>
    suspend fun saveUser(saveUserRequestParam: SaveUserRequestParam): Result<Unit>
}