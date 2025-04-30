package com.maum.note.ui.screen.onboarding.tone

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.model.note.NoteType
import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.usecase.UpdateToneUseCase
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneSideEffect
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

@HiltViewModel
class OnboardingToneViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val updateToneUseCase: UpdateToneUseCase,
) : BaseViewModel<OnboardingToneState, OnboardingToneSideEffect>(
    initialState = OnboardingToneState.init()
) {

    fun onContentValueChange(value: TextFieldValue) {
        setState { copy(content = value) }
    }

    fun onImeVisibilityChanged(isVisible: Boolean) {
        setState { copy(isImeVisible = isVisible) }
    }

    fun onClickNext() {
        viewModelScope.launch(ioDispatcher) {
            val param = getUpdateToneRequestParam()
            updateToneUseCase.invoke(param).onSuccess {
                withContext(mainDispatcher) {
                    navigateToNext()
                }
            }.onFailure {
                // TODO
            }
        }
    }

    private fun getUpdateToneRequestParam(): UpdateToneRequestParam {
        return UpdateToneRequestParam(
            content = currentState.content.text,
            noteType = NoteType.DEFAULT.name
        )
    }

    private fun navigateToNext() {
        postSideEffect { OnboardingToneSideEffect.NavigateToNext }
    }
}