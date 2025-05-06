package com.maum.note.core.designSystem.util.click

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableSharedFlow

interface SingleClickEventInterface {
    fun event(event: () -> Unit)
}

@Composable
fun <T> singleClickEvent(
    throttleDuration: Long = 500L,
    content: @Composable (SingleClickEventInterface) -> T,
): T {
    val eventFlow = remember { MutableSharedFlow<() -> Unit>(extraBufferCapacity = 1) }
    var lastClickTime by remember { mutableLongStateOf(0L) }

    val result = content(
        object : SingleClickEventInterface {
            override fun event(event: () -> Unit) {
                eventFlow.tryEmit(event)
            }
        }
    )

    LaunchedEffect(Unit) {
        eventFlow.collect { clickEvent ->
            val now = System.currentTimeMillis()
            if (now - lastClickTime >= throttleDuration) {
                lastClickTime = now
                clickEvent()
            }
        }
    }

    return result
}
