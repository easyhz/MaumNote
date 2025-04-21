package com.maum.note.data.user.mapper

import com.maum.note.core.common.util.date.DateTimeFormatter
import com.maum.note.core.firebase.user.model.request.SaveUserRequest
import com.maum.note.core.firebase.user.model.response.UserResponse
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.response.UserResponseResult
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val dataTimeFormatter: DateTimeFormatter,
) {
    fun mapToUserResponseData(userResponse: UserResponse): UserResponseResult {
        return UserResponseResult(
            userId = userResponse.id,
            creationTime = dataTimeFormatter.formatTimestampToDateTime(userResponse.creationTime)
        )
    }

    fun mapToSaveUserRequest(saveUserRequestParam: SaveUserRequestParam): SaveUserRequest {
        return SaveUserRequest(
            id = saveUserRequestParam.userId
        )
    }
}