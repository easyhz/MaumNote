package com.maum.note.ui.screen.note.creation.generation

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationSideEffect
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

@HiltViewModel
class NoteGenerationViewModel @Inject constructor(

) : BaseViewModel<NoteGenerationState, NoteGenerationSideEffect>(
    initialState = NoteGenerationState.init()
) {
    init {
        changeTextIndex()
    }

    private fun changeTextIndex() = viewModelScope.launch {
        for (i in 1 until currentState.captionTexts.size) {
            delay(3000L)
            setState { copy(currentTextIndex = i) }
        }
    }

}