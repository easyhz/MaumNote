package com.maum.note.core.designSystem.component.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.model.note.SentenceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentenceCountBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismissRequest: () -> Unit,
    onClick: (SentenceType) -> Unit,
) {
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
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            SentenceType.entries.forEach { item ->
                ListBottomSheetItem(
                    icon = null,
                    title = stringResource(id = item.title),
                    onClick = {
                        onClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SentenceCountBottomSheetPreview() {
    SentenceCountBottomSheet(
        modifier = Modifier,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        onDismissRequest = { },
        onClick = { },
    )
}