package com.maum.note.ui.screen.note.creation.content.contract

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType

/**
 * Date: 2025. 5. 5.
 * Time: 오후 3:43
 */

data class NoteContentState(
    val isLoading: Boolean,
    val noteType: NoteType?,
    val selectedSentenceType: SentenceType,
    val inputText: TextFieldValue,
    val maxCount: Int,
    val isShowSentenceCountBottomSheet: Boolean,
    val dialogMessage: DialogMessage?,
    val errorMessage: ErrorMessage?,
    val absoluteCursorY: Int,
    val isFocused: Boolean,
    val isMoved: Boolean,
    val layoutResult: TextLayoutResult?,
    val cursorOffset: Offset,
) : UiState() {
    companion object {
        fun init(): NoteContentState = NoteContentState(
            isLoading = true,
            noteType = null,
            selectedSentenceType = SentenceType.FOUR_TO_FIVE,
            inputText = TextFieldValue(""),
            maxCount = 100,
            isShowSentenceCountBottomSheet = false,
            dialogMessage = null,
            errorMessage = null,
            absoluteCursorY = 0,
            isFocused = false,
            isMoved = false,
            layoutResult = null,
            cursorOffset = Offset.Zero,
        )
    }
}
