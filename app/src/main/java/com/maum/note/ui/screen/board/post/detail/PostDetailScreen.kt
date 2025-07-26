package com.maum.note.ui.screen.board.post.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.board.PostSection
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import com.maum.note.ui.theme.MainBackground

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    PostDetailScreen(
        modifier = modifier,
        uiState = uiState,
        clearFocus = focusManager::clearFocus,
        navigateUp = navigateUp
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
//        when (sideEffect) {
//
//        }
    }
}

@Composable
private fun PostDetailScreen(
    modifier: Modifier = Modifier,
    uiState: PostDetailState,
    clearFocus: () -> Unit,
    navigateUp: () -> Unit,
) {

    BackHandler {
        clearFocus()
        navigateUp()
    }
    AppScaffold(
        modifier = modifier.noRippleClickable { clearFocus() },
        topBar = {
            TopBar(
                modifier = Modifier.background(MainBackground),
                leftContent = {
                    TopBarIcon(
                        modifier = it,
                        painter = painterResource(id = R.drawable.ic_arrow_left_leading),
                        alignment = Alignment.CenterStart,
                        onClick = {
                            clearFocus()
                            navigateUp()
                        }
                    )
                },
                centerContent = {
                    TopBarText(
                        modifier = it,
                        text = stringResource(id = R.string.board_detail_comment),
                        alignment = Alignment.Center,
                    )
                },
                rightContent = {
                    TopBarIcon(
                        modifier = it,
                        painter = painterResource(R.drawable.ic_more_small_trailing),
                        alignment = Alignment.CenterEnd,
                        onClick = {}
                    )
                },
            )
        },
        bottomBar = {
//            Box(
//                modifier = Modifier
//                    .imePadding()
//                    .fillMaxWidth()
//                    .heightIn(min = 48.dp)
//                    .background(White)
//                    .padding(horizontal = 16.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                AnonymousCheckButton(
//                    isChecked = uiState.isAnonymous,
//                    onClick = onClickAnonymous,
//                )
//            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            item {
                if (uiState.post == null) return@item
                PostSection(
                    post = uiState.post
                ) { }
            }

        }

        uiState.dialogMessage?.let { dialog ->
            BasicDialog(
                title = dialog.title,
                content = dialog.message,
                positiveButton = dialog.positiveButton,
                negativeButton = dialog.negativeButton
            )
        }
    }

}

@Preview
@Composable
private fun PostDetailScreenPreview() {
    PostDetailScreen(
        uiState = PostDetailState.init(),
        clearFocus = { },
        navigateUp = { }
    )
}