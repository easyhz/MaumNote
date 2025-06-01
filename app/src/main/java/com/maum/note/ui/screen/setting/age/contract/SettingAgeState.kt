package com.maum.note.ui.screen.setting.age.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.AgeType

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

data class SettingAgeState(
    val isLoading: Boolean,
    val age: AgeType
) : UiState() {
    companion object {
        fun init(): SettingAgeState = SettingAgeState(
            isLoading = true,
            age = AgeType.MIXED
        )
    }
}
