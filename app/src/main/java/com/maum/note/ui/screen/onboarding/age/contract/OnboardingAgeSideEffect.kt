package com.maum.note.ui.screen.onboarding.age.contract

import com.maum.note.core.common.base.UiSideEffect
import com.maum.note.core.model.note.AgeType

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

sealed class OnboardingAgeSideEffect : UiSideEffect() {
    data class SetPickerAge(val ageType: AgeType): OnboardingAgeSideEffect()
    data object NavigateToNext : OnboardingAgeSideEffect()
}