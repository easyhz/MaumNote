package com.maum.note.core.designSystem.component.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.common.util.date.toDisplayTimeAgo
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.LocalDateTimeFormatter
import com.maum.note.ui.theme.Placeholder
import java.time.LocalDateTime


@Composable
fun AuthorWithTime(
    modifier: Modifier = Modifier,
    appDateTimeFormatter: AppDateTimeFormatter = LocalDateTimeFormatter.current,
    author: String,
    isAnonymous: Boolean,
    createdAt: LocalDateTime
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (isAnonymous) stringResource(R.string.board_anonymous) else author,
            style = AppTypography.body2_regular.copy(
                color = Placeholder
            )
        )

        Box(modifier = Modifier
            .size(2.dp)
            .clip(CircleShape)
            .background(Placeholder)
        )

        Text(
            text = createdAt.toDisplayTimeAgo(context = context, appDateTimeFormatter = appDateTimeFormatter),
            style = AppTypography.body2_regular.copy(
                color = Placeholder
            )
        )
    }
}

@Preview
@Composable
private fun AuthorWithTimePreview() {
    AuthorWithTime(
        appDateTimeFormatter = AppDateTimeFormatter(),
        author = "익명",
        isAnonymous = true,
        createdAt = LocalDateTime.now()
    )
}