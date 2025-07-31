package com.maum.note.core.designSystem.component.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.SubText


interface BottomSheetType {
    val titleId: Int
    val iconId: Int?
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    items: List<T>,
    title: String?,
    onDismissRequest: () -> Unit,
    onClick: (T) -> Unit
) where T: BottomSheetType {
    BasicBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = {
            DragHandle(
                modifier = Modifier.padding(top = 8.dp)
            )
        },
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            title?.let {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = title,
                    style = AppTypography.body3_regular.copy(
                        color = SubText
                    )
                )
            }
            items.forEach { item ->
                ListBottomSheetItem(
                    icon  = item.iconId?.let { painterResource(id = it) },
                    title = stringResource(id = item.titleId),
                    onClick = {
                        onClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

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
            .singleClickable { onClick() }
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