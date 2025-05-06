package com.maum.note.core.designSystem.extension.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.util.click.singleClickEvent
import com.maum.note.ui.theme.SubText


inline fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    singleClickEvent { manager ->
        clickable(
            indication = null,
            enabled = enabled,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() }) {
            manager.event { onClick() }
        }
    }
}

inline fun Modifier.circleClickable(
    enabled: Boolean = true,
    bounded: Boolean = false,
    radius: Dp = 24.dp,
    color: Color = SubText,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    singleClickEvent { manager ->
        clickable(
            indication = ripple(bounded = bounded, radius = radius, color = color),
            enabled = enabled,
            interactionSource = remember { MutableInteractionSource() }) {
            manager.event { onClick() }
        }
    }
}


inline fun Modifier.singleClickable(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    singleClickEvent { manager ->
        clickable(
            enabled = enabled,
        ) {
            manager.event { onClick() }
        }
    }
}