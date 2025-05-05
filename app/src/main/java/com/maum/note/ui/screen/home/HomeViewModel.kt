package com.maum.note.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.domain.note.usecase.FindAllNotesUseCase
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val findAllNotesUseCase: FindAllNotesUseCase
) : BaseViewModel<HomeState, HomeSideEffect>(
    initialState = HomeState.init()
) {
    init {

    }

    private fun findAllNotes() {
        findAllNotesUseCase.invoke().onEach {
            setState { copy(noteList = it.map { it.toNote() }) }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
        }
    }
}