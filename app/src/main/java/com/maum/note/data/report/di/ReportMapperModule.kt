package com.maum.note.data.report.di

import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.data.report.mapper.ReportMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReportMapperModule {

    @Provides
    fun provideReportMapper(
        resourceHelper: ResourceHelper,
    ): ReportMapper {
        return ReportMapper(
            resourceHelper = resourceHelper,
        )
    }
}