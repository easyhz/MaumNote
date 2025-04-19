package com.maum.note.ui.screen.onboarding.start.contract

import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 4. 19.
 * Time: 오전 10:56
 */

data class OnboardingStartState(
    val isLoading: Boolean
) : UiState() {
    companion object {
        fun init(): OnboardingStartState = OnboardingStartState(
            isLoading = true
        )
    }
}
