package com.maum.note.ui.screen.note.creation.generation.contract

import com.maum.note.core.common.base.UiSideEffect
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.core.model.note.Note

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

sealed class NoteGenerationSideEffect : UiSideEffect() {
    data class NavigateUp(val errorMessage: ErrorMessage?) : NoteGenerationSideEffect()
    data class NavigateToNext(val note: Note) : NoteGenerationSideEffect()
}