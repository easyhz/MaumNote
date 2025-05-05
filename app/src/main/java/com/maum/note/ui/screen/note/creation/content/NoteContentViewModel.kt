package com.maum.note.ui.screen.note.creation.content

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.BaseViewModel
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

) : BaseViewModel<NoteContentState, NoteContentSideEffect>(
    initialState = NoteContentState.init()
) {

    fun onInputValueChange(value: TextFieldValue) {
        setState { copy(inputText = value) }
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

}