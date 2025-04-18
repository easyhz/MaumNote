package com.maum.note.ui.screen.setting.contract

import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

data class SettingState(
    val isLoading: Boolean
) : UiState() {
    companion object {
        fun init(): SettingState = SettingState(
            isLoading = true
        )
    }
}
