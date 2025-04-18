package com.maum.note.ui.screen.note.creation.select

import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.model.note.NoteType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.note.creation.select.contract.NoteTypeSelectionSideEffect
import com.maum.note.ui.screen.note.creation.select.contract.NoteTypeSelectionState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 1:39
 */

@HiltViewModel
class NoteTypeSelectionViewModel @Inject constructor(

) : BaseViewModel<NoteTypeSelectionState, NoteTypeSelectionSideEffect>(
    initialState = NoteTypeSelectionState.init()
) {

    fun selectNoteType(noteType: NoteType) {
        if (noteType == uiState.value.selectedNoteType) return
        reduce { copy(selectedNoteType = noteType) }
    }
}