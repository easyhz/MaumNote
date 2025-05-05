package com.maum.note.ui.screen.note.creation.generation.contract

import com.maum.note.core.common.base.UiSideEffect
import com.maum.note.core.model.error.ErrorMessage

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

sealed class NoteGenerationSideEffect : UiSideEffect() {
    data class NavigateUp(val errorMessage: ErrorMessage?) : NoteGenerationSideEffect()
}