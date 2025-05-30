package com.maum.note.ui.screen.setting.setting.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

sealed class SettingSideEffect : UiSideEffect() {
    data object NavigateToToneSetting : SettingSideEffect()
    data object NavigateToAgeSetting : SettingSideEffect()
    data class NavigateToUrl(val url: String) : SettingSideEffect()
}