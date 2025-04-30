package com.maum.note.core.designSystem.component.picker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maum.note.core.designSystem.extension.modifier.fadingEdge
import com.maum.note.core.designSystem.extension.modifier.pxToDp
import com.maum.note.core.designSystem.util.picker.PickerState
import com.maum.note.core.designSystem.util.picker.getItem
import com.maum.note.core.designSystem.util.picker.rememberPickerState
import com.maum.note.ui.theme.SubText
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun <T> Picker(
    items: List<T>,
    state: PickerState<T>,
    modifier: Modifier = Modifier,
    dividerModifier: Modifier = Modifier,
    dividerColor: Color = SubText,
    content: @Composable (modifier: Modifier, index: Int) -> Unit,
) {
    val visibleItemsMiddle = state.visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + items.indexOf(state.initialValue)

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state.listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pxToDp(itemHeightPixels.value)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to SubText,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(state.listState) {
        snapshotFlow { state.listState.firstVisibleItemIndex }
            .map { index -> getItem(items, index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    LaunchedEffect(Unit) {
        state.listState.scrollToItem(listStartIndex)
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = state.listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * state.visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                content(
                    Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height },
                    index
                )
            }
        }

        HorizontalDivider(
            modifier = dividerModifier.offset(y = itemHeightDp * visibleItemsMiddle),
            color = dividerColor
        )

        HorizontalDivider(
            modifier = dividerModifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1)),
            color = dividerColor
        )

    }

}

@Preview
@Composable
private fun Preview() {
    val values = remember { (1..99).map { it.toString() } }
    val valuesPickerState = rememberPickerState<String>("23")

    Picker(
        modifier = Modifier.width(120.dp),
        state = valuesPickerState,
        items = values,
    ) { modifier, index ->
        Box(
            modifier = modifier.width(120.dp).padding(8.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "만",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = getItem(values, index),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 24.sp,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "세",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

        }
    }
}