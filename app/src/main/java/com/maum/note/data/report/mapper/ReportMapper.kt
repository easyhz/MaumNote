package com.maum.note.data.report.mapper

import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.Generate
import com.maum.note.core.supabase.service.report.dto.ReportDto
import com.maum.note.domain.report.model.request.ReportParam
import javax.inject.Inject

class ReportMapper @Inject constructor(
    private val resourceHelper: ResourceHelper
) {
    fun mapToReportDto(userId: String, param: ReportParam): ReportDto = ReportDto(
        id = Generate.randomUUIDv7(),
        userId = userId,
        content = param.content,
        postId = param.postId,
        commentId = param.commentId,
        createdAt = Generate.now(),
    )
}