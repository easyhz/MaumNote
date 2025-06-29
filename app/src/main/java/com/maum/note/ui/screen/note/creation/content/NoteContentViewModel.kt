package com.maum.note.ui.screen.note.creation.content

import android.view.View
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.NoteAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.validation.ValidationInput
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.core.model.note.generation.GenerationNote
import com.maum.note.domain.setting.usecase.age.GetAgeSettingUseCase
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentSideEffect
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 5. 5.
 * Time: 오후 3:43
 */

@HiltViewModel
class NoteContentViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val validationInput: ValidationInput,
    private val resourceHelper: ResourceHelper,
    private val getAgeSettingUseCase: GetAgeSettingUseCase,
) : BaseViewModel<NoteContentState, NoteContentSideEffect>(
    initialState = NoteContentState.init()
) {

    init {
        init()
    }

    private fun init() {
        val noteTypeArgs: String? = savedStateHandle["noteType"]
        val noteType = noteTypeArgs?.let { NoteType.getByValue(it) }
        if (noteType == null) {
            navigateUp()
            return
        }
        setState { copy(noteType = noteType) }
        getAgeSetting()
    }

    private fun getAgeSetting() {
        viewModelScope.launch(ioDispatcher) {
            getAgeSettingUseCase.invoke(Unit)
                .onSuccess {
                    withContext(mainDispatcher) {
                        val age = AgeType.getByValue(it) ?: AgeType.MIXED
                        setState { copy(ageType = age) }
                    }
                }
        }
    }

    fun onInputValueChange(value: TextFieldValue) {
        setState { copy(inputText = value) }
    }

    fun onClickSentenceCount() {
        val isShow = currentState.isShowSentenceCountBottomSheet
        setState { copy(isShowSentenceCountBottomSheet = !isShow) }
    }

    fun onClickNext() {
        if (!isValidContentInput()) {
            handleOverTextLength()
            return
        }
        val noteType = currentState.noteType ?: return navigateUp()
        val ageType = currentState.ageType ?: return navigateUp()
        val generationNote = GenerationNote(
            ageType = ageType.name,
            noteType = noteType.name,
            sentenceCountType = currentState.selectedSentenceType.name,
            inputContent = currentState.inputText.text,
        )
        logEvent()
        postSideEffect { NoteContentSideEffect.NavigateToNext(generationNote = generationNote) }
    }

    fun onDismissRequestSentenceBottomSheet() {
        setState { copy(isShowSentenceCountBottomSheet = false) }
    }

    fun onClickSentenceBottomSheetItem(sentenceType: SentenceType) {
        setState { copy(selectedSentenceType = sentenceType) }
        onDismissRequestSentenceBottomSheet()
    }

    fun setErrorMessage(errorMessage: ErrorMessage?) {
        setState { copy(errorMessage = errorMessage) }
    }

    private fun navigateUp() {
        postSideEffect { NoteContentSideEffect.NavigateUp }
    }

    private fun isValidContentInput(): Boolean {
        val text = currentState.inputText.text
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

    fun onGloballyPositioned(view: View) {
        val cursorRect =
            currentState.layoutResult?.getCursorRect(currentState.inputText.selection.end)
        if (cursorRect != null && currentState.cursorOffset.y != cursorRect.topLeft.y) {
            setState { copy(cursorOffset = cursorRect.topLeft) }
        }
        val windowPos = IntArray(2)
        view.getLocationInWindow(windowPos)
        val absoluteCursorPos =
            currentState.cursorOffset + Offset(windowPos[0].toFloat(), windowPos[1].toFloat())
        if (currentState.absoluteCursorY == absoluteCursorPos.y.toInt()) return
        setState { copy(absoluteCursorY = absoluteCursorPos.y.toInt()) }
    }

    fun setLayoutResult(textLayoutResult: TextLayoutResult) {
        setState { copy(layoutResult = textLayoutResult) }
    }

    fun onChangeFocus(hasFocus: Boolean) {
        setState { copy(isFocused = hasFocus, isMoved = !hasFocus) }
    }

    private fun logEvent() {
        val sentenceCountEvent = currentState.selectedSentenceType.getAddNoteLogEvent()
        val ageEvent = currentState.ageType?.getAddNoteLogEvent()
        AnalyticsManager.logEvent(sentenceCountEvent)
        AnalyticsManager.logEvent(NoteAnalyticsEvent.NOTE_CREATE)
        ageEvent?.let { AnalyticsManager.logEvent(it) }
    }
}