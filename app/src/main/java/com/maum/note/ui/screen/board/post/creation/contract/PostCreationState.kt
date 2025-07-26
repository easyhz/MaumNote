package com.maum.note.ui.screen.board.post.creation.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.error.ErrorMessage

/**
 * Date: 2025. 7. 25.
 * Time: 오후 6:15
 */

data class PostCreationState(
    val isLoading: Boolean,
    val titleText: TextFieldValue,
    val contentText: TextFieldValue,
    val isAnonymous: Boolean,
    val dialogMessage: DialogMessage?,
    val errorMessage: ErrorMessage?,
) : UiState() {
    companion object {
        fun init(): PostCreationState = PostCreationState(
            isLoading = false,
            titleText = TextFieldValue(""),
            contentText = TextFieldValue(""),
            isAnonymous = false,
            dialogMessage = null,
            errorMessage = null,
        )
    }
}
