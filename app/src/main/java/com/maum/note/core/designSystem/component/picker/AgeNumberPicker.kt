package com.maum.note.core.designSystem.component.picker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.util.picker.PickerState
import com.maum.note.core.designSystem.util.picker.getItem
import com.maum.note.core.designSystem.util.picker.rememberPickerState
import com.maum.note.core.model.note.AgeType
import com.maum.note.ui.theme.AppTypography

@Composable
fun AgeNumberPicker(
    modifier: Modifier = Modifier,
    state: PickerState<AgeType>,
    values: List<AgeType> = AgeType.entries,
) {
    Picker(
        modifier = modifier,
        state = state,
        items = values,
    ) { contentModifier, index ->
        Text(
            modifier = contentModifier.padding(8.dp),
            text = stringResource(getItem(values, index).title),
            style = AppTypography.body1_semiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val valuesPickerState = rememberPickerState(AgeType.NONE)
    AgeNumberPicker(
        state = valuesPickerState,
    )
}