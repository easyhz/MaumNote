package com.maum.note.ui.screen.note.creation.content.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
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
    val isShowNext: Boolean,
) : UiState() {
    companion object {
        fun init(): NoteContentState = NoteContentState(
            isLoading = true,
            noteType = null,
            selectedSentenceType = SentenceType.TWO_TO_THREE,
            inputText = TextFieldValue(""),
            maxCount = 100,
            isShowSentenceCountBottomSheet = false,
            isShowNext = false,
        )
    }
}
