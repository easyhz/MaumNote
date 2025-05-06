package com.maum.note.ui.screen.note.detail

import androidx.lifecycle.SavedStateHandle
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.helper.serializable.SerializableHelper
import com.maum.note.core.common.util.url.urlDecode
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.note.NoteDetailType
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
    private val savedStateHandle: SavedStateHandle,
    private val serializableHelper: SerializableHelper,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<NoteDetailState, NoteDetailSideEffect>(
    initialState = NoteDetailState.init()
) {

    init {
        init()
    }

    private fun init() {
        val noteDetailArgs: String? = savedStateHandle["noteDetailArgs"]
        val noteDetail = serializableHelper.deserialize(noteDetailArgs, Note::class.java)
            ?: return navigateUp()

        val note = noteDetail.copy(
            inputContent = noteDetail.inputContent.urlDecode(),
            result = noteDetail.result.urlDecode()
        )
        setState { copy(detailContent = convertNoteDetail(note), date = note.createdAt) }
    }

    private fun convertNoteDetail(note: Note): List<NoteDetailType> {
        return NoteDetailType.entries(
            noteType = note.noteType,
            typeValue = note.result,
            ageValue = resourceHelper.getString(note.ageType.title),
            sentenceValue = resourceHelper.getString(note.sentenceCountType.title),
            inputValue = note.inputContent,
        )
    }

    private fun navigateUp() {
        postSideEffect { NoteDetailSideEffect.NavigateUp }
    }

    fun onClickCopyButton() {
        val noteDetailType = currentState.detailContent.find { it is NoteDetailType.Type} ?: return
        postSideEffect { NoteDetailSideEffect.CopyToClipboard(noteDetailType.content) }
        showSnackBar(
            resourceHelper = resourceHelper,
            value = R.string.note_copy_success
        ) { NoteDetailSideEffect.ShowSnackBar(it) }
    }

}