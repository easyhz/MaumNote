package com.maum.note.ui.screen.splash.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

sealed class SplashSideEffect : UiSideEffect() {
    data object NavigateToOnboarding : SplashSideEffect()
    data object NavigateToHome : SplashSideEffect()
    data object NavigateUp : SplashSideEffect()
    data class NavigateToUrl(val url: String) : SplashSideEffect()
}