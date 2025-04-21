package com.maum.note.data.user.repository

import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.data.user.mapper.UserMapper
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.response.UserResponseResult
import com.maum.note.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper,
): UserRepository {
    override fun isLogin(): Result<Boolean> {
        return runCatching {
            userRemoteDataSource.isLogin()
        }
    }

    override fun getUserId(): Result<String> {
        return runCatching {
            userRemoteDataSource.getUserId()
        }
    }

    override suspend fun getUser(uid: String): Result<UserResponseResult?> {
        return runCatching {
            userRemoteDataSource.getUser(uid)?.let { userMapper.mapToUserResponseData(it) }
        }
    }

    override suspend fun signInAnonymously(): Result<String> {
        return runCatching {
            userRemoteDataSource.signInAnonymously()
        }
    }

    override suspend fun saveUser(saveUserRequestParam: SaveUserRequestParam): Result<Unit> {
        return runCatching {
            userRemoteDataSource.saveUser(
                saveUserRequest = userMapper.mapToSaveUserRequest(saveUserRequestParam)
            )
        }
    }

}