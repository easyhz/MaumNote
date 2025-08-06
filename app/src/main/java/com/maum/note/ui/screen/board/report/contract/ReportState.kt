package com.maum.note.ui.screen.board.report.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage

/**
 * Date: 2025. 8. 6.
 * Time: 오후 12:25
 */

data class ReportState(
    val isLoading: Boolean,
    val contentText: TextFieldValue,
    val dialogMessage: DialogMessage?,
) : UiState() {
    companion object {
        fun init(): ReportState = ReportState(
            isLoading = true,
            contentText = TextFieldValue(),
            dialogMessage = null
        )
    }
}
