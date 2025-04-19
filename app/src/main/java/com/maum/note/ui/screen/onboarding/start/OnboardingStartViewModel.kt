package com.maum.note.ui.screen.onboarding.start

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.onboarding.start.contract.OnboardingStartSideEffect
import com.maum.note.ui.screen.onboarding.start.contract.OnboardingStartState

/**
 * Date: 2025. 4. 19.
 * Time: 오전 10:56
 */

@HiltViewModel
class OnboardingStartViewModel @Inject constructor(

) : BaseViewModel<OnboardingStartState, OnboardingStartSideEffect>(
    initialState = OnboardingStartState.init()
) {

}