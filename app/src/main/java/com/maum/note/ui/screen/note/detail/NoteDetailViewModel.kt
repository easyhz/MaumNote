package com.maum.note.ui.screen.note.detail

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.note.detail.contract.NoteDetailSideEffect
import com.maum.note.ui.screen.note.detail.contract.NoteDetailState

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

@HiltViewModel
class NoteDetailViewModel @Inject constructor(

) : BaseViewModel<NoteDetailState, NoteDetailSideEffect>(
    initialState = NoteDetailState.init()
) {

}