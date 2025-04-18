package com.maum.note.ui.screen.onboarding.tone

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneSideEffect
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

@HiltViewModel
class OnboardingToneViewModel @Inject constructor(

) : BaseViewModel<OnboardingToneState, OnboardingToneSideEffect>(
    initialState = OnboardingToneState.init()
) {

    fun onContentValueChange(value: TextFieldValue) {
        setState { copy(content = value) }
    }

    fun onImeVisibilityChanged(isVisible: Boolean) {
        setState { copy(isImeVisible = isVisible) }
    }

}