package com.maum.note.domain.report.repository

import com.maum.note.domain.report.model.request.ReportParam

interface ReportRepository {
    suspend fun report(param: ReportParam): Result<Unit>
}