package com.maum.note.ui.screen.setting.tone.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.model.note.NoteType

/**
 * Date: 2025. 4. 19.
 * Time: 오전 11:21
 */

data class ToneSettingState(
    val isLoading: Boolean,
    val contents: Map<NoteType, TextFieldValue>
) : UiState() {
    companion object {
        fun init(): ToneSettingState = ToneSettingState(
            isLoading = true,
            contents = NoteType.entries.associateWith { TextFieldValue("") }
        )
    }
}
