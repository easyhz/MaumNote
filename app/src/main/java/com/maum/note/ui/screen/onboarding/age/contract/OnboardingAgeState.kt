package com.maum.note.ui.screen.onboarding.age.contract

import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

data class OnboardingAgeState(
    val isLoading: Boolean
) : UiState() {
    companion object {
        fun init(): OnboardingAgeState = OnboardingAgeState(
            isLoading = true
        )
    }
}
