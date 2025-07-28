package com.maum.note.core.designSystem.component.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubBackground

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = Primary,
        trackColor = SubBackground,
        strokeCap = StrokeCap.Square,
        gapSize = 0.dp
    ) {
        drawStopIndicator(
            drawScope = this,
            stopSize = 0.dp,
            color = Primary,
            strokeCap = StrokeCap.Square
        )
    }
}

@Preview
@Composable
private fun ProgressBarPreview() {
    ProgressBar(
        progress = 0.2f
    )
}