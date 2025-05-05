package com.maum.note.ui.screen.note.creation.generation.contract

import com.maum.note.R
import com.maum.note.core.common.base.UiState

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

data class NoteGenerationState(
    val isLoading: Boolean,
    val captionTexts: List<Int>,
    val currentTextIndex: Int,
) : UiState() {
    companion object {
        fun init(): NoteGenerationState = NoteGenerationState(
            isLoading = true,
            captionTexts = listOf(
                R.string.note_generation_loading_1,
                R.string.note_generation_loading_2,
                R.string.note_generation_loading_3,
                R.string.note_generation_loading_4,
                R.string.note_generation_loading_5,
            ),
            currentTextIndex = 0,
        )
    }
}
