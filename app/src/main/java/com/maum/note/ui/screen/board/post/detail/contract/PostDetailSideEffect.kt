package com.maum.note.ui.screen.board.post.detail.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

sealed class PostDetailSideEffect : UiSideEffect() {
    data object NavigateUp: PostDetailSideEffect()
    data object NavigateToBoard: PostDetailSideEffect()
}