package com.maum.note.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MaumNoteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
