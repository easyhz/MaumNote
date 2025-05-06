package com.maum.note.ui.screen.setting.setting.contract

import com.maum.note.BuildConfig
import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.setting.Configuration

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

data class SettingState(
    val isLoading: Boolean,
    val configuration: Configuration,
) : UiState() {
    companion object {
        fun init(): SettingState = SettingState(
            isLoading = true,
            configuration = Configuration(
                androidVersion = BuildConfig.VERSION_NAME,
                notionUrl = "https://jdeoks.notion.site/1dab1dc234bc809cb604fa5dc4ebe865",
                maintenanceNotice = "",
            )
        )
    }
}
