package com.maum.note.core.designSystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.extension.modifier.dropShadow
import com.maum.note.core.designSystem.util.button.ButtonColor
import com.maum.note.ui.theme.AppTypography


@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = AppTypography.bold20,
    buttonColor: ButtonColor = ButtonColor(),
    enabled: Boolean = true,
    height: Dp = 52.dp,
    onClick: () -> Unit,
) {
    val onClickInvoke: () -> Unit = remember(enabled, onClick) {
        if (enabled) onClick else { { } }
    }
    val backgroundColor = remember(enabled, buttonColor.containerColor) {
        if (enabled) buttonColor.containerColor else buttonColor.disabledContainerColor
    }
    val textColor = remember(enabled, buttonColor.contentColor) {
        if (enabled) buttonColor.contentColor else buttonColor.disabledContentColor
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
            .height(height)
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                color = Color.Black.copy(0.25f),
                offsetY = 6.dp,
                blur = 12.dp
            )
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(enabled) { onClickInvoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun MainButtonPrev() {
    MainButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Button", onClick = { }
    )

}