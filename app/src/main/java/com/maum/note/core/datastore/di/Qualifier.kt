package com.maum.note.core.datastore.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AgeDataStorePreference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConfigurationDataStorePreference
