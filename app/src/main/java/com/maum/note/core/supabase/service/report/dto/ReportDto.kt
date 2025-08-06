package com.maum.note.core.supabase.service.report.dto

import com.maum.note.core.common.util.Generate
import com.maum.note.core.supabase.constant.Table
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportDto(
    @SerialName(Table.REPORTS.ID)
    val id: String = Generate.randomUUIDv7(),
    @SerialName(Table.REPORTS.USER_ID)
    val userId: String,
    @SerialName(Table.REPORTS.CONTENT)
    val content: String,
    @SerialName(Table.REPORTS.POST_ID)
    val postId: String?,
    @SerialName(Table.REPORTS.COMMENT_ID)
    val commentId: String?,
    @SerialName(Table.NOTES.CREATED_AT)
    val createdAt: Instant,
)
