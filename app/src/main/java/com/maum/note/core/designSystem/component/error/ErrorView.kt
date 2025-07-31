package com.maum.note.core.designSystem.component.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maum.note.R
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.White

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.no_data_error),
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = AppTypography.body1.copy(
                color = SubText,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            ),
        )

        onClick?.let {
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .clip(CircleShape)
                    .background(Primary)
                    .singleClickable(onClick = it)
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.retry),
                    style = AppTypography.body3_regular.copy(
                        color = White,
                        textAlign = TextAlign.Center
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorViewPreview() {
    ErrorView(
        onClick = { }
    )
}