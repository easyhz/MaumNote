package com.maum.note.core.supabase.service.report.service

import com.maum.note.core.supabase.service.report.dto.ReportDto

interface ReportService {
    suspend fun insertReport(reportDto: ReportDto)
}