package com.maum.note.data.user.repository

import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.data.user.mapper.UserMapper
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.response.AuthUser
import com.maum.note.domain.user.model.response.User
import com.maum.note.domain.user.repository.UserRepository
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
        return runCatching {
            userRemoteDataSource.insertUser(
                userDto = userMapper.mapToUserDto(saveUserRequestParam)
            )
        }
    }

}