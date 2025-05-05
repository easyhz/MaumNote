package com.maum.note.core.designSystem.component.section

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.White

@Composable
fun SelectionSection(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    minHeight: Dp = 0.dp,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = AppTypography.body1_semiBold,
        )
        Row(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .clickable(enabled = onClick != null) {
                    onClick?.invoke()
                }.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = minHeight),
                text = value,
                style = AppTypography.body1.copy(textAlign = TextAlign.Start),
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun SelectionSectionPreview() {
    SelectionSection(
        title = "Title",
        value = "Value",
        minHeight = 0.dp,
    )
}