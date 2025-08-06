package com.maum.note.data.report.datasource.remote

import com.maum.note.core.supabase.service.report.dto.ReportDto

interface ReportRemoteDataSource {
    suspend fun insertReport(reportDto: ReportDto)
}