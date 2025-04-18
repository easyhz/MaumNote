package com.maum.note.ui.screen.onboarding.tone.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

data class OnboardingToneState(
    val isLoading: Boolean,
    val content: TextFieldValue,
    val isImeVisible: Boolean,
) : UiState() {
    companion object {
        fun init(): OnboardingToneState = OnboardingToneState(
            isLoading = true,
            content = TextFieldValue(""),
            isImeVisible = false,
        )
    }
}
