package com.maum.note.ui.screen.board.report

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.board.BoardReport
import com.maum.note.domain.report.model.request.ReportParam
import com.maum.note.domain.report.usecase.ReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.board.report.contract.ReportSideEffect
import com.maum.note.ui.screen.board.report.contract.ReportState
import kotlinx.coroutines.launch

/**
 * Date: 2025. 8. 6.
 * Time: 오후 12:25
 */

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportUseCase: ReportUseCase,
    private val resourceHelper: ResourceHelper,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<ReportState, ReportSideEffect>(
    initialState = ReportState.init()
) {
    init {
        init()
    }

    private fun init() {
        val postId: String? = savedStateHandle["postId"]
        val commentId: String? = savedStateHandle["commentId"]
        println("postId : $postId, commentId : $commentId")
        val boardReport = BoardReport(
            postId = postId,
            commentId = commentId,
        )
        if(!validData(boardReport)) return

        setState { copy(boardReport = boardReport, isLoading = false) }
    }

    fun onClickNext() {
        report()
    }

    fun onValueChangeContent(value: TextFieldValue) {
        setState { copy(contentText = value) }
    }


    private fun report() {
        viewModelScope.launch {
            if(!validData()) return@launch
            setLoading(true)
            val param = ReportParam(
                content = currentState.contentText.text,
                postId = currentState.boardReport.postId,
                commentId = currentState.boardReport.commentId,
            )
            reportUseCase.invoke(param).onSuccess {
                setSuccessDialog()
            }.onFailure {
                it.printStackTrace()
                setErrorDialog(it)
            }
            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

    private fun navigateUp() {
        postSideEffect { ReportSideEffect.NavigateUp }
    }


    private fun setSuccessDialog() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.report_dialog_title),
                message = resourceHelper.getString(R.string.report_dialog_content),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::navigateUp
                )
            )
        )
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

    private fun validData(boardReport: BoardReport = currentState.boardReport): Boolean {
        if (boardReport.postId.isNullOrBlank() && boardReport.commentId.isNullOrBlank()) {
            setNoDataErrorDialog()
            return false
        }
        return true
    }

    private fun setNoDataErrorDialog() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.error_dialog_title),
                message = resourceHelper.getString(R.string.no_data_error),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::navigateUp
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


}