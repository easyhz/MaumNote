package com.maum.note.ui.screen.board.post.detail.model

import androidx.annotation.StringRes
import com.maum.note.R
import com.maum.note.core.designSystem.component.bottomSheet.BottomSheetType

sealed class MoreBottomSheet(
    open val list: List<BottomSheetType>,
    @StringRes val title: Int,
    open val targetId: String,
) {
    data class Post(
        override val list: List<BottomSheetType>,
        override val targetId: String
    ): MoreBottomSheet(
        list = list,
        title = R.string.board_bottom_sheet_title,
        targetId = targetId
    )
    data class Comment(
        override val list: List<BottomSheetType>,
        override val targetId: String
    ): MoreBottomSheet(
        list = list,
        title = R.string.comment_bottom_sheet_title,
        targetId = targetId
    )
}