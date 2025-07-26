package com.maum.note.ui.screen.board.board.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

sealed class BoardSideEffect : UiSideEffect() {
    data class NavigateToUrl(val url: String) : BoardSideEffect()
}