package com.maum.note.core.datastore.di

import com.maum.note.core.datastore.age.AgeDataStore
import com.maum.note.core.datastore.age.AgeDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun bindAgeDataStore(
        ageDataStoreImpl: AgeDataStoreImpl
    ): AgeDataStore
}