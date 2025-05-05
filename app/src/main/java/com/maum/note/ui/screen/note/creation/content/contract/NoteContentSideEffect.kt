package com.maum.note.ui.screen.note.creation.content.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 5. 5.
 * Time: 오후 3:43
 */

sealed class NoteContentSideEffect : UiSideEffect() {
    data object NavigateUp : NoteContentSideEffect()
}