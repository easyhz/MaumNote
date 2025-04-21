package com.maum.note.core.common.di.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Dispatcher(AppDispatchers.IO)
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Dispatcher(AppDispatchers.DEFAULT)
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Dispatcher(AppDispatchers.MAIN)
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}