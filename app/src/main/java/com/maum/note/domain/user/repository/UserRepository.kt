package com.maum.note.domain.user.repository

import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.request.UpdateUserStudentRequestParam
import com.maum.note.domain.user.model.response.AuthUser
import com.maum.note.domain.user.model.response.User

interface UserRepository {
    fun isLogin(): Result<Boolean>
    fun getCurrentUser(): AuthUser?
    suspend fun fetchUser(uid: String): User?
    suspend fun signInAnonymously()
    suspend fun saveUser(saveUserRequestParam: SaveUserRequestParam): Result<Unit>
    suspend fun signOut()
    suspend fun clearUserSession()

    suspend fun updateUserStudentAge(updateUserStudentRequestParam: UpdateUserStudentRequestParam)
}