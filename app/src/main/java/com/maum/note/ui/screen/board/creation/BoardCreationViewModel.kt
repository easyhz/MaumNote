package com.maum.note.ui.screen.board.creation

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.ui.screen.board.creation.contract.BoardCreationSideEffect
import com.maum.note.ui.screen.board.creation.contract.BoardCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Date: 2025. 7. 25.
 * Time: 오후 6:15
 */

@HiltViewModel
class BoardCreationViewModel @Inject constructor(

) : BaseViewModel<BoardCreationState, BoardCreationSideEffect>(
    initialState = BoardCreationState.init()
) {

    fun onClickNext() {
        // TODO 작성
    }

    fun onValueChangeTitle(value: TextFieldValue) {
        setState { copy(titleText = value) }
    }

    fun onValueChangeContent(value: TextFieldValue) {
        setState { copy(contentText = value) }
    }

    fun setIsAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
    }
}