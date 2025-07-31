package com.maum.note.ui.screen.board.post.detail.contract

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.ui.screen.board.post.detail.model.MoreBottomSheet

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

data class PostDetailState(
    val isLoading: Boolean,
    val userId: String?,
    val post: Post?,
    val comments: List<Comment>,
    val commentText: TextFieldValue,
    val isAnonymous: Boolean,
    val dialogMessage: DialogMessage?,
    val errorMessage: ErrorMessage?,
    val moreBottomSheet: MoreBottomSheet?
) : UiState() {
    companion object {
        fun init(): PostDetailState = PostDetailState(
            isLoading = true,
            userId = null,
            post = null,
            comments = emptyList(),
            commentText = TextFieldValue(),
            isAnonymous = false,
            dialogMessage = null,
            errorMessage = null,
            moreBottomSheet = null,
        )
    }
}
