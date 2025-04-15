package com.maum.note.core.designSystem.component.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.maum.note.core.designSystem.component.snackbar.AppSnackBarHost
import com.maum.note.core.designSystem.util.snackbar.SnackBarType
import com.maum.note.core.designSystem.util.snackbar.snackBarPadding
import com.maum.note.ui.theme.LocalSnackBarHostState
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText


@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MainBackground,
    contentColor: Color = MainText,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackBarHostState = LocalSnackBarHostState.current
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = {
            AppSnackBarHost(
                modifier = Modifier.snackBarPadding(SnackBarType.Default).imePadding(),
                hostState = snackBarHostState
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}