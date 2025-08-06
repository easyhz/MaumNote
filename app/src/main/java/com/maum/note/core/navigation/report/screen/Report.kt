package com.maum.note.core.navigation.report.screen

import kotlinx.serialization.Serializable


@Serializable
data class Report(
    val postId: String?,
    val commentId: String?
)
