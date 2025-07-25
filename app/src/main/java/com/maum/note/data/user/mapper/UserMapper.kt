package com.maum.note.data.user.mapper

import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.supabase.service.user.dto.UserDto
import com.maum.note.domain.user.model.request.SaveUserRequestParam
import com.maum.note.domain.user.model.response.AuthUser
import com.maum.note.domain.user.model.response.User
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.datetime.Clock
import javax.inject.Inject

class UserMapper @Inject constructor(

) {
    fun mapUserInfoToAuthUser(userInfo: UserInfo?): AuthUser? {
        if (userInfo == null) return null
        return AuthUser(
            id = userInfo.id,
            createdAt = userInfo.createdAt?.let {
                AppDateTimeFormatter.convertInstantToDateTime(it)
            }
        )
    }

    fun mapUserDtoToUser(userDto: UserDto): User {
        return User(
            id = userDto.id,
            nickname = userDto.nickname,
            hasAgreedToTerms = userDto.hasAgreedToTerms,
            studentAge = userDto.studentAge,
            creationTime = AppDateTimeFormatter.convertInstantToDateTime(userDto.creationTime),
            isDeleted = userDto.isDeleted,
        )
    }

    fun mapToUserDto(saveUserRequestParam: SaveUserRequestParam): UserDto {
        return UserDto(
            id = saveUserRequestParam.userId,
            creationTime = Clock.System.now()
        )
    }
}