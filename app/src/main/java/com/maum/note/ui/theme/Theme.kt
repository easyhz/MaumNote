package com.maum.note.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.maum.note.core.common.util.date.AppDateTimeFormatter

val LocalSnackBarHostState = staticCompositionLocalOf { SnackbarHostState() }
val LocalDateTimeFormatter = staticCompositionLocalOf<AppDateTimeFormatter> {
    error("No DateTimeFormatter provided")
}

@Composable
fun MaumNoteTheme(
    appDateTimeFormatter: AppDateTimeFormatter = AppDateTimeFormatter(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDateTimeFormatter provides appDateTimeFormatter
    ) {
        MaterialTheme(
            content = content
        )
    }
}
