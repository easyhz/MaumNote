package com.maum.note.core.designSystem.util.dialog

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.ButtonShapeColor
import com.maum.note.ui.theme.DestructiveRed
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary

data class BasicDialogButton(
    val text: String,
    val backgroundColor: Color,
    val style: TextStyle = AppTypography.heading5_semiBold,
    val onClick: () -> Unit
) {
    companion object {
        fun default(text: String, onClick: () -> Unit): BasicDialogButton {
            return BasicDialogButton(
                text = text,
                style = AppTypography.heading5_semiBold.copy(color = MainBackground),
                backgroundColor = Primary,
                onClick = onClick
            )
        }

        fun cancel(text: String, onClick: () -> Unit): BasicDialogButton {
            return BasicDialogButton(
                text = text,
                style = AppTypography.heading5_semiBold.copy(color = MainText),
                backgroundColor = ButtonShapeColor,
                onClick = onClick
            )
        }

        fun delete(text: String, onClick: () -> Unit): BasicDialogButton {
            return BasicDialogButton(
                text = text,
                style = AppTypography.heading5_semiBold.copy(color = MainBackground),
                backgroundColor = DestructiveRed,
                onClick = onClick
            )
        }
    }
}