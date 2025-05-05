package com.maum.note.ui.screen.home.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.Note

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

data class HomeState(
    val isLoading: Boolean,
    val noteList: List<Note>
) : UiState() {
    companion object {
        fun init(): HomeState = HomeState(
            isLoading = true,
            noteList = emptyList()
        )
    }
}
