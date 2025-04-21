package com.maum.note.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.user.useacse.CheckUserStepUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.splash.contract.SplashSideEffect
import com.maum.note.ui.screen.splash.contract.SplashState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val checkUserStepUseCase: CheckUserStepUseCase
) : BaseViewModel<SplashState, SplashSideEffect>(
    initialState = SplashState.init()
) {

    init {
        setUser()
    }

    private fun setUser() = viewModelScope.launch(ioDispatcher) {
        checkUserStepUseCase.invoke(Unit).onSuccess {
            withContext(mainDispatcher) {
                handleUserStep(it)
            }
        }.onFailure {
            setState { copy(isLoading = false) }
        }
    }

    private fun handleUserStep(userStep: UserStep) {
        when(userStep) {
            UserStep.NewUserToOnboarding, UserStep.ExistingUserToOnboarding -> {
                postSideEffect { SplashSideEffect.NavigateToOnboarding }
            }
            UserStep.AlreadyLoginToMain -> {
                postSideEffect { SplashSideEffect.NavigateToMain }
            }
        }
    }
}