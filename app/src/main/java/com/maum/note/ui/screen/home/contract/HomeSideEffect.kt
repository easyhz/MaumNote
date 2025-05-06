package com.maum.note.ui.screen.home.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

sealed class HomeSideEffect : UiSideEffect() {
    data class ShowSnackBar(val message: String) : HomeSideEffect()
    data class CopyToClipboard(val result: String) : HomeSideEffect()
}