package com.maum.note.core.designSystem.component.loading

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.maum.note.ui.theme.NoteBackground
import com.maum.note.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshIndicator(
    modifier: Modifier = Modifier,
    state: PullToRefreshState,
    isRefreshing: Boolean,
    containerColor: Color = NoteBackground,
    color: Color = Primary,
    threshold: Dp = PositionalThreshold,
) {
    Indicator(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        containerColor = containerColor,
        color = color,
        threshold = threshold
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PullToRefreshIndicatorPreview() {
    PullToRefreshIndicator(
        state = PullToRefreshState(),
        isRefreshing = true
    )

}