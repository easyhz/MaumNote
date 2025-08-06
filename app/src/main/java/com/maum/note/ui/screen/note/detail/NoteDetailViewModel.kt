package com.maum.note.ui.screen.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.NoteDetailAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.url.urlDecode
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.common.OwnerBottomSheet
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.note.NoteDetailType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.domain.note.usecase.DeleteNoteUseCase
import com.maum.note.ui.screen.note.detail.contract.NoteDetailSideEffect
import com.maum.note.ui.screen.note.detail.contract.NoteDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : BaseViewModel<NoteDetailState, NoteDetailSideEffect>(
    initialState = NoteDetailState.init()
) {

    init {
        init()
    }

    private fun init() {
        val id: String? = savedStateHandle["id"]
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
        setState { copy(noteId = id, detailContent = convertNoteDetail(note), date = note.createdAt, isLoading = false) }
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
        logEventCopyButtonClick()
        showSnackBar(
            resourceHelper = resourceHelper,
            value = R.string.note_copy_success
        ) { NoteDetailSideEffect.ShowSnackBar(it) }
    }

    fun onClickMore() {
        setState { copy(isShowBottomSheet = true) }
    }

    fun hideBottomSheet() {
        setState { copy(isShowBottomSheet = false) }
    }

    fun onClickBottomSheetItem(item: OwnerBottomSheet) {
        when(item) {
            OwnerBottomSheet.DELETE -> setDeleteDialog()
        }
    }

    private fun setDeleteDialog() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.note_delete_dialog_title),
                message = resourceHelper.getString(R.string.note_delete_dialog_content),
                positiveButton = BasicDialogButton.delete(
                    text = resourceHelper.getString(R.string.dialog_delete_positive),
                    onClick = {
                        hideDialog()
                        deleteNote()
                    }
                ),
                negativeButton = BasicDialogButton.cancel(
                    text = resourceHelper.getString(R.string.dialog_delete_negative),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun deleteNote() {
        viewModelScope.launch {
            setLoading(true)
            val noteId = currentState.noteId
            deleteNoteUseCase.invoke(noteId).onSuccess {
                navigateToHome()
            }.onFailure {
                setErrorDialog(it)
                it.printStackTrace()
            }
            setLoading(false)
        }
    }

    private fun logEventCopyButtonClick() {
        AnalyticsManager.logEvent(NoteDetailAnalyticsEvent.NOTE_DETAIL_COPIED)
    }

    private fun setErrorDialog(error: Throwable) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.error_dialog_title),
                message = resourceHelper.getString(error.handleError()),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::hideDialog
                )
            )
        )
    }


    private fun setDialog(message: DialogMessage?) {
        setState { copy(dialogMessage = message) }
    }


    private fun hideDialog() {
        setDialog(null)
    }

    private fun navigateToHome() {
        postSideEffect { NoteDetailSideEffect.NavigateToHome }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

}