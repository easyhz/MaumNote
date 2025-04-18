package com.maum.note.core.designSystem.util.textField

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


sealed class TextFieldType(
    open val alignment: Alignment,
    open val minHeight: Dp,
    open val paddingValues: PaddingValues,
) {
    data class Multiple(
        override val alignment: Alignment = Alignment.TopStart,
        override val minHeight: Dp = 200.dp,
        override val paddingValues: PaddingValues = PaddingValues(8.dp),
    ): TextFieldType(
        alignment = alignment,
        minHeight = minHeight,
        paddingValues = paddingValues,
    )
}