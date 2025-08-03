package com.maum.note.ui.screen.setting.profile.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 8. 3.
 * Time: 오후 10:26
 */

sealed class ProfileSideEffect : UiSideEffect() {
    data class ShowSnackBar(val message: String) : ProfileSideEffect()
    data class ShowToast(val message: String) : ProfileSideEffect()
    data object NavigateUp: ProfileSideEffect()
}