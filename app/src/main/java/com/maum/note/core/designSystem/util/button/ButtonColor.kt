package com.maum.note.core.designSystem.util.button

import androidx.compose.ui.graphics.Color
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubText

data class ButtonColor(
    val containerColor: Color = Primary,
    val contentColor: Color = MainBackground,
    val disabledContainerColor: Color = Placeholder,
    val disabledContentColor: Color = SubText,
)
