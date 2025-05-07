package com.maum.note.data.note.di

import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.data.note.mapper.NoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object NoteMapperModule {
    @Provides
    fun provideNoteMapper(
        resourceHelper: ResourceHelper,
        appDateTimeFormatter: AppDateTimeFormatter,
    ): NoteMapper {
        return NoteMapper(
            resourceHelper = resourceHelper,
            appDateTimeFormatter = appDateTimeFormatter
        )
    }
}