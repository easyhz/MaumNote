package com.maum.note.ui.screen.board.board.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.board.Post

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

data class BoardState(
    val isLoading: Boolean,
    val postList: List<Post>
) : UiState() {
    companion object {
        fun init(): BoardState = BoardState(
            isLoading = true,
            postList = emptyList()
        )
    }
}
