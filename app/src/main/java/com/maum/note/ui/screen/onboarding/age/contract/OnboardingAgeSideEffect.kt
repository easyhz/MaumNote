package com.maum.note.ui.screen.onboarding.age.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

sealed class OnboardingAgeSideEffect : UiSideEffect() {
    data object NavigateToNext : OnboardingAgeSideEffect()
}