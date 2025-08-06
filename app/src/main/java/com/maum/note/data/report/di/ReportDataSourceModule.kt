package com.maum.note.data.report.di

import com.maum.note.data.report.datasource.remote.ReportRemoteDataSource
import com.maum.note.data.report.datasource.remote.ReportRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ReportDataSourceModule {
    @Binds
    fun bindReportRemoteDataSource(
        reportRemoteDataSourceImpl: ReportRemoteDataSourceImpl
    ): ReportRemoteDataSource
}