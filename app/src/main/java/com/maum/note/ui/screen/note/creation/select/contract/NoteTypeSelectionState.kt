package com.maum.note.ui.screen.note.creation.select.contract

import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.NoteType

/**
 * Date: 2025. 4. 18.
 * Time: 오후 1:39
 */

data class NoteTypeSelectionState(
    val isLoading: Boolean,
    val selectedNoteType: NoteType?,
) : UiState() {
    companion object {
        fun init(): NoteTypeSelectionState = NoteTypeSelectionState(
            isLoading = true,
            selectedNoteType = null,
        )
    }
}
