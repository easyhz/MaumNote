package com.maum.note.core.designSystem.component.topbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.extension.modifier.circleClickable
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.Primary

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    leftContent: (@Composable (Modifier) -> Unit)? = null,
    centerContent: (@Composable (Modifier) -> Unit)? = null,
    rightContent: (@Composable (Modifier) -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        leftContent?.invoke(Modifier.align(Alignment.CenterStart))
        centerContent?.invoke(Modifier.align(Alignment.Center))
        rightContent?.invoke(Modifier.align(Alignment.CenterEnd))
    }
}

@Composable
fun TopBarIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    alignment: Alignment,
    tint: Color = MainText,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .noRippleClickable { onClick() },
        contentAlignment = alignment
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun TopBarText(
    modifier: Modifier = Modifier,
    text: String,
    alignment: Alignment,
    enabled: Boolean = true,
    color: Color = MainText,
    onClick: (() -> Unit)? = null,
) {
    val color by animateColorAsState(
        targetValue = if (enabled) color else Placeholder,
    )
    Box(
        modifier = modifier
            .circleClickable(onClick != null && enabled) { onClick?.invoke() },
        contentAlignment = alignment
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = AppTypography.heading5_semiBold,
            color = color
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        leftContent = {
            TopBarIcon(
                modifier = it,
                painter = painterResource(id = R.drawable.ic_arrow_left_leading),
                alignment = Alignment.CenterStart,
                onClick = { }
            )
        },
    )
}