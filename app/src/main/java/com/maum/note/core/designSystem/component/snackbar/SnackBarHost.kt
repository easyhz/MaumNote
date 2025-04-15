package com.maum.note.core.designSystem.component.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = hostState
    ) {
        AppSnackBar(snackBarData = it)
    }
}