package com.maum.note.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarHostState = staticCompositionLocalOf { SnackbarHostState() }

@Composable
fun MaumNoteTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        MaterialTheme(
            content = content
        )
    }
}
