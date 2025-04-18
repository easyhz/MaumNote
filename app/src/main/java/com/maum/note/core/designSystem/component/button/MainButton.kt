package com.maum.note.core.designSystem.component.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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
    isImeVisible: Boolean? = null,
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
    val padding by getPadding(isImeVisible = isImeVisible)
    val cornerRadius by getCornerRadius(isImeVisible = isImeVisible)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
            .imePadding()
            .height(height)
            .then(
                if (isImeVisible == null) {
                    Modifier.dropShadow(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Black.copy(0.25f),
                        offsetY = 6.dp,
                        blur = 12.dp
                    )
                } else Modifier
            )
            .clip(RoundedCornerShape(cornerRadius))
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

@Composable
fun getPadding(
    isImeVisible: Boolean?,
    defaultPadding: Dp = 20.dp,
    changedPadding: Dp = 0.dp
): State<Dp> {
    return animateDpAsState(
        targetValue = if (isImeVisible == true) {
            changedPadding
        } else {
            defaultPadding
        },
        label = ""
    )
}

@Composable
private fun getCornerRadius(
    isImeVisible: Boolean?,
    defaultPadding: Dp = 8.dp,
    changedPadding: Dp = 0.dp
): State<Dp> {
    return animateDpAsState(
        targetValue = if (isImeVisible == true) {
            changedPadding
        } else {
            defaultPadding
        },
        label = ""
    )
}

@Preview
@Composable
private fun MainButtonPrev() {
    MainButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Button", onClick = { }
    )

}