package com.maum.note.ui.screen.onboarding.tone.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

data class OnboardingToneState(
    val isLoading: Boolean,
    val toneId: String?,
    val content: TextFieldValue,
    val enabledButton: Boolean,
    val isImeVisible: Boolean,
    val maxCount: Int,
    val dialogMessage: DialogMessage?,
) : UiState() {
    companion object {
        fun init(): OnboardingToneState = OnboardingToneState(
            isLoading = true,
            toneId = null,
            content = TextFieldValue(""),
            enabledButton = true,
            isImeVisible = false,
            maxCount = 300,
            dialogMessage = null,
        )
    }
}
