package com.maum.note.ui.screen.home.contract

import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

data class HomeState(
    val isLoading: Boolean,
    val noteList: List<String>
) : UiState() {
    companion object {
        fun init(): HomeState = HomeState(
            isLoading = true,
            noteList = listOf(
                "Sample Note 1",
                "Sample Note 2",
                "Sample Note 3",
                "Sample Note 4",
                "Sample Note 5",
                "Sample Note 6",
            )
        )
    }
}
