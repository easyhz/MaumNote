package com.maum.note.ui.screen.onboarding.tone

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.validation.ValidationInput
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.note.NoteType
import com.maum.note.domain.setting.model.tone.request.UpdateToneRequestParam
import com.maum.note.domain.setting.usecase.tone.GetAllSelectedTonesUseCase
import com.maum.note.domain.setting.usecase.tone.UpdateToneUseCase
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneSideEffect
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

@HiltViewModel
class OnboardingToneViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val getAllSelectedTonesUseCase: GetAllSelectedTonesUseCase,
    private val updateToneUseCase: UpdateToneUseCase,
    private val resourceHelper: ResourceHelper,
    private val validationInput: ValidationInput,
) : BaseViewModel<OnboardingToneState, OnboardingToneSideEffect>(
    initialState = OnboardingToneState.init()
) {

    init {
        getTones()
    }

    private fun getTones() {
        viewModelScope.launch(ioDispatcher) {
            getAllSelectedTonesUseCase.invoke(Unit).onSuccess { tones ->
                val tone = tones.find { it.noteType == NoteType.DEFAULT.name }
                if (tone == null) return@launch
                setState {
                    copy(
                        content = TextFieldValue(tone.content),
                        enabledButton = isValidContentInput(tone.content)
                    )
                }
            }
        }
    }

    fun onContentValueChange(value: TextFieldValue) {
        setState { copy(content = value, enabledButton = isValidContentInput(value.text)) }
    }

    fun onImeVisibilityChanged(isVisible: Boolean) {
        setState { copy(isImeVisible = isVisible) }
    }

    fun onClickNext() {
        viewModelScope.launch(ioDispatcher) {
            val param = getUpdateToneRequestParam()
            updateToneUseCase.invoke(param).onSuccess {
                navigateToNext()
            }.onFailure {
                navigateToNext()
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

    private fun isValidContentInput(text: String): Boolean {
        val maxCount = currentState.maxCount
        return validationInput.isValidContentInput(text = text, maxCount = maxCount)
    }

    private fun handleOverTextLength() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.note_creation_content_over_text_length_title),
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.maintenance_dialog_button),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = AppTypography.heading5_semiBold.copy(color = MainBackground),
            backgroundColor = Primary,
            onClick = onClick
        )
    }

    private fun setDialog(message: DialogMessage?) {
        setState { copy(dialogMessage = message) }
    }

    private fun hideDialog() {
        setDialog(null)
    }
}