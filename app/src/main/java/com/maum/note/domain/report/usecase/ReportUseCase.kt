package com.maum.note.domain.report.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.report.model.request.ReportParam
import com.maum.note.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
): BaseUseCase<ReportParam, Unit>() {
    override suspend fun invoke(param: ReportParam): Result<Unit> {
        return reportRepository.report(param)
    }
}