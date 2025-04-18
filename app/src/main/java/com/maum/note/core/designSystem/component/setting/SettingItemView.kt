package com.maum.note.core.designSystem.component.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.model.setting.EtcSettingItem
import com.maum.note.core.model.setting.MajorSettingItem
import com.maum.note.core.model.setting.SettingItem
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.White

@Composable
fun SettingItemView(
    modifier: Modifier = Modifier,
    majorSettingItem: MajorSettingItem,
    onClickItem: (SettingItem) -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 44.dp)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(majorSettingItem.stringResId),
                style = AppTypography.heading5_semiBold,
                color = MainText
            )
        }
        majorSettingItem.items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(item.enabledClick) { onClickItem(item) }
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(item.stringResId),
                    style = AppTypography.body1,
                    color = MainText,
                )
                when(item) {
                    EtcSettingItem.VERSION -> {
                        Text(
                            text = item.getValue() ?: "",
                            style = AppTypography.body2_regular,
                            color = MainText,
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    SettingItemView(
        majorSettingItem = MajorSettingItem.NOTE
    ) { }
}