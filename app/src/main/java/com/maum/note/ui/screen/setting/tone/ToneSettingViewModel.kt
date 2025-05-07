package com.maum.note.ui.screen.setting.tone

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
import com.maum.note.domain.setting.usecase.tone.UpdateAllToneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingSideEffect
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.ButtonShapeColor
import com.maum.note.ui.theme.DestructiveRed
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Date: 2025. 4. 19.
 * Time: 오전 11:21
 */

@HiltViewModel
class ToneSettingViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val getAllSelectedTonesUseCase: GetAllSelectedTonesUseCase,
    private val updateAllToneUseCase: UpdateAllToneUseCase,
    private val validationInput: ValidationInput,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<ToneSettingState, ToneSettingSideEffect>(
    initialState = ToneSettingState.init()
) {

    init {
        init()
    }

    private fun init() {
        getAllSelectedTones()
    }

    fun onContentValueChange(
        noteType: NoteType,
        newValue: TextFieldValue
    ) {
        setState { copy(contents = contents.toMutableMap().apply { this[noteType] = newValue }) }
    }

    fun onClickSave() {
        checkValidInput()?.let {
            handleOverTextLength(it)
            return
        }
        updateAllTone()
    }

    fun onClickNavigateUp() {
        if (isChanged()) {
            handleChangedDialog()
            return
        }
        navigateUp()
    }

    private fun getAllSelectedTones() {
        viewModelScope.launch(ioDispatcher) {
            getAllSelectedTonesUseCase.invoke(Unit).onSuccess { tones ->
                val contents = tones.mapNotNull { tone ->
                    NoteType.getByValue(tone.noteType)?.let { type ->
                        type to TextFieldValue(tone.content)
                    }
                }.toMap()
                withContext(mainDispatcher) {
                    setState { copy(contents = contents, originalContents = contents) }
                }
            }.onFailure {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                }
            }
        }
    }

    private fun updateAllTone() {
        viewModelScope.launch(ioDispatcher) {
            val param = getUpdateToneRequestParam()
            updateAllToneUseCase.invoke(param).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                }
            }.onFailure {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                }
            }
        }
    }

    private fun getUpdateToneRequestParam(): List<UpdateToneRequestParam> {
        return currentState.contents.map { (noteType, textFieldValue) ->
            UpdateToneRequestParam(
                noteType = noteType.name,
                content = textFieldValue.text
            )
        }
    }

    private fun checkValidInput(): NoteType? {
        currentState.contents.entries.forEach { (noteType, text) ->
            val maxCount = noteType.maxCount
            if (!validationInput.isValidContentInput(text = text.text, maxCount = maxCount)) {
                return noteType
            }
        }
        return null
    }

    private fun handleOverTextLength(noteType: NoteType) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.note_creation_content_over_text_length_title),
                message = resourceHelper.getString(noteType.title),
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

    private fun navigateUp() {
        postSideEffect { ToneSettingSideEffect.NavigateUp }
    }

    private fun isChanged(): Boolean {
        return currentState.contents.entries.any { (noteType, text) ->
            val originalText = currentState.originalContents[noteType]?.text
            originalText != text.text
        }
    }

    private fun handleChangedDialog() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.setting_note_tone_do_not_save_title),
                message = resourceHelper.getString(R.string.setting_note_tone_do_not_save_message),
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.setting_note_tone_positive_button),
                    onClick = ::navigateUp
                ).copy(
                    backgroundColor = DestructiveRed,
                ),
                negativeButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.setting_note_tone_negative_button),
                    onClick = ::hideDialog
                ).copy(
                    backgroundColor = ButtonShapeColor,
                    style = AppTypography.heading5_semiBold.copy(color = MainText)
                )
            )
        )
    }
}