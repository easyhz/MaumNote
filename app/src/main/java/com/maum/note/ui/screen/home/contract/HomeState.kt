package com.maum.note.ui.screen.home.contract

import com.maum.note.BuildConfig
import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.setting.Configuration

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

data class HomeState(
    val isLoading: Boolean,
    val noteList: List<Note>,
    val needNotificationPermission: Boolean,
    val configuration: Configuration
) : UiState() {
    companion object {
        fun init(): HomeState = HomeState(
            isLoading = false,
            noteList = emptyList(),
            needNotificationPermission = false,
            configuration = Configuration(
                androidVersion = BuildConfig.VERSION_NAME,
                notionUrl = "https://jdeoks.notion.site/1dab1dc234bc809cb604fa5dc4ebe865",
                maintenanceNotice = "",
                adContents = emptyList()
            )
        )
    }
}
