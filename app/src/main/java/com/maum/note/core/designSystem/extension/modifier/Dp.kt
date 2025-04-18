package com.maum.note.core.designSystem.extension.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun pxToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }