package com.maum.note.core.designSystem.util.picker

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T> rememberPickerState(initial: T, visibleItemsCount: Int = 5): PickerState<T> {
    val listState = rememberLazyListState()
    return remember { PickerState(initial, listState, visibleItemsCount) }
}

class PickerState<T>(
    val initialValue: T,
    internal val listState: LazyListState,
    internal val visibleItemsCount: Int
) {
    var selectedItem by mutableStateOf(initialValue)

    suspend fun animateToItem(value: T, items: List<T>) {
        val index = items.indexOf(value)
        if (index != -1) {
            val listScrollMiddle = Integer.MAX_VALUE / 2
            val targetIndex = listScrollMiddle - listScrollMiddle % items.size - (visibleItemsCount / 2) + index
            listState.animateScrollToItem(targetIndex)
        }
    }
}


