package com.maum.note.ui.screen.splash.contract

import com.maum.note.BuildConfig
import com.maum.note.core.common.base.UiState
import com.maum.note.core.designSystem.util.dialog.DialogMessage

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

data class SplashState(
    val isLoading: Boolean,
    val version: String,
    val dialogMessage: DialogMessage?,
) : UiState() {
    companion object {
        fun init(): SplashState = SplashState(
            isLoading = true,
            version = "v${BuildConfig.VERSION_NAME}",
            dialogMessage = null,
        )
    }
}
