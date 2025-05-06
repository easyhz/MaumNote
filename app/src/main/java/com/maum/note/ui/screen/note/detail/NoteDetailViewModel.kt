package com.maum.note.ui.screen.note.detail

import androidx.lifecycle.SavedStateHandle
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.url.urlDecode
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.note.NoteDetailType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.ui.screen.note.detail.contract.NoteDetailSideEffect
import com.maum.note.ui.screen.note.detail.contract.NoteDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<NoteDetailState, NoteDetailSideEffect>(
    initialState = NoteDetailState.init()
) {

    init {
        init()
    }

    private fun init() {
        val id: Long? = savedStateHandle["id"]
        val noteType: String? = savedStateHandle["noteType"]
        val ageType: String? = savedStateHandle["ageType"]
        val sentenceCountType: String? = savedStateHandle["sentenceCountType"]
        val inputContent: String? = savedStateHandle["inputContent"]
        val result: String? = savedStateHandle["result"]
        val createdAt: String? = savedStateHandle["createdAt"]

        if (id == null || noteType.isNullOrBlank() || ageType.isNullOrBlank() ||
            sentenceCountType.isNullOrBlank() || createdAt.isNullOrBlank()
        ) {
            return navigateUp()
        }
        val note = Note(
            id = id,
            noteType = NoteType.getByValue(noteType)!!,
            ageType = AgeType.getByValue(ageType)!!,
            sentenceCountType = SentenceType.getByValue(sentenceCountType)!!,
            inputContent = inputContent?.urlDecode() ?: "",
            result = result?.urlDecode() ?: "",
            createdAt = LocalDateTime.parse(createdAt)
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