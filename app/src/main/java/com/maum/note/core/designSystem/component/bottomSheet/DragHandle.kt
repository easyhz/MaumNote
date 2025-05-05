package com.maum.note.core.designSystem.component.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.Placeholder

@Composable
fun DragHandle(
    modifier: Modifier = Modifier,
    color: Color = Placeholder
) {
    Box(
        modifier = modifier
            .height(4.dp)
            .width(32.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Preview
@Composable
private fun DragHandlePreview() {
    DragHandle()
}