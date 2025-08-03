package com.maum.note.ui.screen.setting.nickname.contract

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.R
import com.maum.note.core.common.base.UiState
import com.maum.note.ui.theme.DestructiveRed
import com.maum.note.ui.theme.SubText

/**
 * Date: 2025. 8. 3.
 * Time: 오후 10:26
 */

data class NicknameState(
    val isLoading: Boolean,
    val isImeVisible: Boolean,
    val enabledButton: Boolean,
    val buttonLoading: Boolean,
    val nicknameText: TextFieldValue,
    val captionType: CaptionType
) : UiState() {
    companion object {
        fun init(): NicknameState = NicknameState(
            isLoading = true,
            isImeVisible = false,
            enabledButton = true,
            buttonLoading = false,
            nicknameText = TextFieldValue(""),
            captionType = CaptionType.DEFAULT
        )
    }
}


enum class CaptionType(
    @StringRes val captionText: Int,
    val color: Color
) {
    DEFAULT(
        captionText = R.string.setting_board_profile_nickname_caption_default,
        color = SubText
    ), DUPLICATED(
        captionText = R.string.setting_board_profile_nickname_caption_duplicated,
        color = DestructiveRed
    ), LENGTH(
        captionText = R.string.setting_board_profile_nickname_caption_length,
        color = DestructiveRed
    ), BLANK(
        captionText = R.string.setting_board_profile_nickname_caption_blank,
        color = DestructiveRed
    )
}