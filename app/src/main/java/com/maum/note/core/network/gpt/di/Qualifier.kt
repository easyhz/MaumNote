package com.maum.note.core.network.gpt.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GptRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GptClient

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class Debug

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class HttpLoggingLevel

