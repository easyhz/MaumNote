package com.maum.note.core.designSystem.util.textField

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


sealed class TextFieldType(
    open val verticalAlignment: Alignment.Vertical,
    open val minHeight: Dp,
    open val paddingValues: PaddingValues,
) {
    data class Multiple(
        override val verticalAlignment: Alignment.Vertical = Alignment.Top,
        override val minHeight: Dp = 200.dp,
        override val paddingValues: PaddingValues = PaddingValues(8.dp),
    ): TextFieldType(
        verticalAlignment = verticalAlignment,
        minHeight = minHeight,
        paddingValues = paddingValues,
    )
}