package com.maum.note.core.designSystem.util.textField

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


sealed class TextFieldType(
    open val alignment: Alignment,
    open val minHeight: Dp,
    open val maxHeight: Dp,
    open val paddingValues: PaddingValues,
) {
    data class Multiple(
        override val alignment: Alignment = Alignment.TopStart,
        override val minHeight: Dp = 200.dp,
        override val maxHeight: Dp = Int.MAX_VALUE.dp,
        override val paddingValues: PaddingValues = PaddingValues(8.dp),
    ): TextFieldType(
        alignment = alignment,
        minHeight = minHeight,
        maxHeight = minHeight,
        paddingValues = paddingValues,
    )

    data class Single(
        override val alignment: Alignment = Alignment.CenterStart,
        override val minHeight: Dp = 48.dp,
        override val maxHeight: Dp = 48.dp,
        override val paddingValues: PaddingValues = PaddingValues(8.dp),
    ): TextFieldType(
        alignment = alignment,
        minHeight = minHeight,
        maxHeight = minHeight,
        paddingValues = paddingValues,
    )
}