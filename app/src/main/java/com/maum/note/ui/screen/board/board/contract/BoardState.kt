package com.maum.note.ui.screen.board.board.contract

import com.maum.note.BuildConfig
import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.board.Post
import com.maum.note.core.model.setting.Configuration

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

data class BoardState(
    val isLoading: Boolean,
    val postList: List<Post>,
    val configuration: Configuration
) : UiState() {
    companion object {
        fun init(): BoardState = BoardState(
            isLoading = true,
            postList = emptyList(),
            configuration = Configuration(
                androidVersion = BuildConfig.VERSION_NAME,
                notionUrl = "https://jdeoks.notion.site/1dab1dc234bc809cb604fa5dc4ebe865",
                maintenanceNotice = "",
                adContents = emptyList()
            )
        )
    }
}
