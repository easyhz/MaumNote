package com.maum.note.core.common.di.helper

import com.maum.note.core.common.helper.serializable.MoshiSerializableHelper
import com.maum.note.core.common.helper.serializable.SerializableHelper
import com.maum.note.core.common.helper.log.AppLogger
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.common.helper.resource.DefaultResourceHelper
import com.maum.note.core.common.helper.resource.ResourceHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface HelperModule {

    @Binds
    @Singleton
    fun bindLogger(
        appLogger: AppLogger
    ): Logger

    @Binds
    @Singleton
    fun bindSerializableHelper(
        moshiSerializableHelper: MoshiSerializableHelper
    ): SerializableHelper

    @Binds
    @Singleton
    fun bindResourceHelper(
        defaultResourceHelper: DefaultResourceHelper
    ): ResourceHelper

}