package com.maum.note.ui.screen.onboarding.age

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeSideEffect
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

@HiltViewModel
class OnboardingAgeViewModel @Inject constructor(

) : BaseViewModel<OnboardingAgeState, OnboardingAgeSideEffect>(
    initialState = OnboardingAgeState.init()
) {

}