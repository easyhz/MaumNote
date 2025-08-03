package com.maum.note.ui.screen.setting.nickname.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 8. 3.
 * Time: 오후 10:26
 */

sealed class NicknameSideEffect : UiSideEffect() {
    data class ShowSnackBar(val message: String) : NicknameSideEffect()
    data class ShowToast(val message: String) : NicknameSideEffect()
    data object NavigateUp: NicknameSideEffect()
}