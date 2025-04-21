package com.maum.note.data.user.datasource.remote

import com.maum.note.core.firebase.user.model.request.SaveUserRequest
import com.maum.note.core.firebase.user.model.response.UserResponse
import com.maum.note.core.firebase.user.service.UserService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserRemoteDataSource {
    override fun isLogin(): Boolean {
        return userService.isLogin()
    }
    override fun getUserId(): String {
        return userService.getUserId()
    }
    override suspend fun getUser(uid: String): UserResponse? {
        return userService.getUser(uid)
    }
    override suspend fun signInAnonymously(): String {
        return userService.signInAnonymously()
    }
    override suspend fun saveUser(saveUserRequest: SaveUserRequest) {
        return userService.saveUser(saveUserRequest)
    }
}