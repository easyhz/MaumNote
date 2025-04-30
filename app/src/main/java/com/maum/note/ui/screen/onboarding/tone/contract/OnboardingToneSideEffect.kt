package com.maum.note.ui.screen.onboarding.tone.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

sealed class OnboardingToneSideEffect : UiSideEffect() {
    data object NavigateToNext : OnboardingToneSideEffect()
}