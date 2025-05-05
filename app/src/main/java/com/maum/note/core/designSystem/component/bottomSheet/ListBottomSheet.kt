package com.maum.note.core.designSystem.component.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText

@Composable
fun ListBottomSheetItem(
    modifier: Modifier = Modifier,
    icon: Painter?,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = it,
                contentDescription = null,
                tint = MainText
            )
        }
        Text(
            text = title,
            style = AppTypography.body2_regular,
            color = MainText
        )
    }
}