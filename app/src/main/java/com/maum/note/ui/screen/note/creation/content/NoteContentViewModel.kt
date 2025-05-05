package com.maum.note.ui.screen.note.creation.content

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentSideEffect
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentState

/**
 * Date: 2025. 5. 5.
 * Time: 오후 3:43
 */

@HiltViewModel
class NoteContentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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
    }

    fun onInputValueChange(value: TextFieldValue) {
        val isShowNext = value.text.isNotBlank() && value.text.length <= currentState.maxCount
        setState { copy(inputText = value, isShowNext = isShowNext) }
    }

    fun onClickSentenceCount() {
        val isShow = currentState.isShowSentenceCountBottomSheet
        setState { copy(isShowSentenceCountBottomSheet = !isShow) }
    }

    fun onClickNext() {
//        postSideEffect { NoteContentSideEffect.NavigateToNext }
    }

    fun onDismissRequestSentenceBottomSheet() {
        setState { copy(isShowSentenceCountBottomSheet = false) }
    }

    fun onClickSentenceBottomSheetItem(sentenceType: SentenceType) {
        setState { copy(selectedSentenceType = sentenceType) }
        onDismissRequestSentenceBottomSheet()
    }

    private fun navigateUp() {
        postSideEffect { NoteContentSideEffect.NavigateUp }
    }

}