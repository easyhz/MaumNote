package com.maum.note.core.supabase.service.report.service

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.report.dto.ReportDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ReportServiceImpl @Inject constructor(
    private val postgrest: Postgrest
): ReportService {
    override suspend fun insertReport(reportDto: ReportDto) {
        postgrest.from(Table.REPORTS.name).insert(reportDto)
    }
}