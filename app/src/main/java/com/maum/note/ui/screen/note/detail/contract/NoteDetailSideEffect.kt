package com.maum.note.ui.screen.note.detail.contract

import com.maum.note.core.common.base.UiSideEffect

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

sealed class NoteDetailSideEffect : UiSideEffect() {
    data object NavigateUp : NoteDetailSideEffect()
    data object NavigateToHome : NoteDetailSideEffect()
    data class CopyToClipboard(val text: String) : NoteDetailSideEffect()
    data class ShowSnackBar(val message: String) : NoteDetailSideEffect()
}