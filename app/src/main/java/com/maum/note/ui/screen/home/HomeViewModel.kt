package com.maum.note.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.model.note.Note
import com.maum.note.domain.note.usecase.FindAllNotesUseCase
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val findAllNotesUseCase: FindAllNotesUseCase,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<HomeState, HomeSideEffect>(
    initialState = HomeState.init()
) {
    init {
        findAllNotes()
    }

    private fun findAllNotes() {
        findAllNotesUseCase.invoke()
            .flowOn(ioDispatcher)
            .onEach {
                setState { copy(noteList = it.map { it.toNote() }, isLoading = false) }
            }.launchIn(viewModelScope)
    }

    fun onClickCopyButton(note: Note) {
        postSideEffect { HomeSideEffect.CopyToClipboard(note.result) }
        showSnackBar(
            resourceHelper = resourceHelper,
            value = R.string.note_copy_success
        ) { HomeSideEffect.ShowSnackBar(it) }
    }
}