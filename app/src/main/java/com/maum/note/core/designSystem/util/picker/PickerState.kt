package com.maum.note.core.designSystem.util.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T> rememberPickerState(initial: T) = remember { PickerState(initial) }

class PickerState<T>(initial: T) {
    val initialValue: T = initial
    var selectedItem by mutableStateOf(initial)
}

