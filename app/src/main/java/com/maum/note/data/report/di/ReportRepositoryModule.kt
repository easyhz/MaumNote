package com.maum.note.data.report.di

import com.maum.note.data.report.repository.ReportRepositoryImpl
import com.maum.note.domain.report.repository.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ReportRepositoryModule {
    @Binds
    fun bindReportRepository(
        reportRepositoryImpl: ReportRepositoryImpl
    ): ReportRepository
}