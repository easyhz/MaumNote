package com.maum.note.core.common.di.dispatcher

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: AppDispatchers)

enum class AppDispatchers {
    DEFAULT,
    IO,
    MAIN,
}