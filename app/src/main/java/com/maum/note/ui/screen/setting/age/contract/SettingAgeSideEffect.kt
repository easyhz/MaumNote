package com.maum.note.ui.screen.setting.age.contract

import com.maum.note.core.common.base.UiSideEffect
import com.maum.note.core.model.note.AgeType

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

sealed class SettingAgeSideEffect : UiSideEffect() {
    data class SetPickerAge(val ageType: AgeType): SettingAgeSideEffect()
    data object NavigateToNext : SettingAgeSideEffect()
}