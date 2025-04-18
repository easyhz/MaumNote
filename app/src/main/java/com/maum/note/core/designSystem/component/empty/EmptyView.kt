package com.maum.note.core.designSystem.component.empty

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.AppTypography

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = com.maum.note.R.string.note_empty),
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTypography.body2.copy(
                color = SubText
            ),
        )
    }
}

@Preview
@Composable
private fun EmptyViewPreview() {
    EmptyView()
}