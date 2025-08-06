package com.maum.note.data.report.datasource.remote

import com.maum.note.core.supabase.service.report.dto.ReportDto
import com.maum.note.core.supabase.service.report.service.ReportService
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val reportService: ReportService
): ReportRemoteDataSource {
    override suspend fun insertReport(reportDto: ReportDto) {
        return reportService.insertReport(reportDto)
    }
}