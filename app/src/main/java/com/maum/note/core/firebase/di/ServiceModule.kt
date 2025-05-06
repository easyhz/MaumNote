package com.maum.note.core.firebase.di

import com.maum.note.core.firebase.configuration.service.ConfigurationService
import com.maum.note.core.firebase.configuration.service.ConfigurationServiceImpl
import com.maum.note.core.firebase.user.service.UserService
import com.maum.note.core.firebase.user.service.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {
    @Binds
    fun bindUserService(
        userServiceImpl: UserServiceImpl
    ): UserService

    @Binds
    fun bindConfigurationService(
        configurationServiceImpl: ConfigurationServiceImpl
    ): ConfigurationService

}