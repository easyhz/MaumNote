package com.maum.note.core.designSystem.util.dialog

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.maum.note.ui.theme.AppTypography

data class BasicDialogButton(
    val text: String,
    val backgroundColor: Color,
    val style: TextStyle = AppTypography.heading5_semiBold,
    val onClick: () -> Unit
)