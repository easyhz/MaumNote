package com.maum.note.ui.screen.note.detail.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.NoteDetailType
import java.time.LocalDateTime

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

data class NoteDetailState(
    val isLoading: Boolean,
    val detailContent: List<NoteDetailType>,
    val date: LocalDateTime,
) : UiState() {
    companion object {
        fun init(): NoteDetailState = NoteDetailState(
            isLoading = true,
            detailContent = NoteDetailType.empty,
            date = LocalDateTime.now(),
        )
    }
}
