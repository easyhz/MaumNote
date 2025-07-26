package com.maum.note.core.navigation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.maum.note.R
import com.maum.note.core.navigation.board.screen.Board
import com.maum.note.core.navigation.home.screen.Home


enum class BottomMenuTabs(
    val qualifierName: String,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int,
) {
    NOTE(
        qualifierName = Home::class.java.name,
        iconId = R.drawable.ic_note,
        label = R.string.bottom_bar_tab_note
    ), BOARD(
        qualifierName = Board::class.java.name,
        iconId = R.drawable.ic_board,
        label = R.string.bottom_bar_tab_board
    )
}