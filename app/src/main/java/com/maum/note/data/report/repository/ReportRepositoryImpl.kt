package com.maum.note.data.report.repository

import com.maum.note.core.common.error.AppError
import com.maum.note.data.report.datasource.remote.ReportRemoteDataSource
import com.maum.note.data.report.mapper.ReportMapper
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.domain.report.model.request.ReportParam
import com.maum.note.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportMapper: ReportMapper,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val reportRemoteDataSource: ReportRemoteDataSource
): ReportRepository {
    override suspend fun report(param: ReportParam): Result<Unit> = runCatching {
        val id = userRemoteDataSource.getCurrentUser()?.id ?: throw AppError.NoUserDataError
        val report = reportMapper.mapToReportDto(userId = id, param = param)
        reportRemoteDataSource.insertReport(report)
    }
}