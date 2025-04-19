package com.maum.note.core.designSystem.component.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.White

@Composable
fun DetailSection(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    minHeight: Dp = 0.dp,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = AppTypography.body1_semiBold,
        )

        SelectionContainer {
            Box(
                modifier = Modifier
                    .heightIn(min = 48.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().heightIn(min = minHeight),
                    text = value,
                    style = AppTypography.body1.copy(
                        textAlign = TextAlign.Start,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetailSection(
        title = "Title",
        value = "ValueValue",
        minHeight = 100.dp,
    )
}