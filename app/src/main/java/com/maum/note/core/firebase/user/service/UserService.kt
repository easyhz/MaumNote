package com.maum.note.core.firebase.user.service

import com.maum.note.core.firebase.user.model.request.SaveUserRequest
import com.maum.note.core.firebase.user.model.response.UserResponse

interface UserService {
    fun isLogin(): Boolean
    fun getUserId(): String
    suspend fun getUser(uid: String): UserResponse?
    suspend fun signInAnonymously(): String
    suspend fun saveUser(saveUserRequest: SaveUserRequest)
}