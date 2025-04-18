package com.maum.note.ui.screen.splash

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.splash.contract.SplashSideEffect
import com.maum.note.ui.screen.splash.contract.SplashState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel<SplashState, SplashSideEffect>(
    initialState = SplashState.init()
) {

}