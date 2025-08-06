package com.maum.note.ui.screen.note.detail.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.note.NoteDetailType
import java.time.LocalDateTime

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

data class NoteDetailState(
    val isLoading: Boolean,
    val noteId: String,
    val detailContent: List<NoteDetailType>,
    val date: LocalDateTime,
    val isShowBottomSheet: Boolean,
    val dialogMessage: DialogMessage?,
) : UiState() {
    companion object {
        fun init(): NoteDetailState = NoteDetailState(
            isLoading = true,
            noteId = "",
            detailContent = NoteDetailType.empty,
            date = LocalDateTime.now(),
            isShowBottomSheet = false,
            dialogMessage = null
        )
    }
}
