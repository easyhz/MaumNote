package com.maum.note.ui.screen.board.post.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.board.CommentSection
import com.maum.note.core.designSystem.component.section.board.PostSection
import com.maum.note.core.designSystem.component.textField.CommentTextField
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.White

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
        navigateUp = navigateUp,
        onValueChange = viewModel::onCommentTextChanged,
        onClickAnonymous = viewModel::onClickAnonymous,
        onClickSend = viewModel::onClickSend
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
    onValueChange: (TextFieldValue) -> Unit = { },
    onClickAnonymous: () -> Unit = { },
    onClickSend: () -> Unit = { }
) {

    BackHandler {
        clearFocus()
        navigateUp()
    }
    AppScaffold(
        modifier = modifier.noRippleClickable { clearFocus() },
        topBar = {
            TopBar(
                modifier = Modifier.background(White),
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
            CommentTextField(
                modifier = Modifier.imePadding(),
                value = uiState.commentText,
                onValueChange = onValueChange,
                isAnonymous = uiState.isAnonymous,
                onClickAnonymous = onClickAnonymous,
                onClickSend = onClickSend
            )
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
                Box(modifier = Modifier.height(8.dp).fillMaxWidth())
            }

            items(uiState.comments) { item ->
                CommentSection(
                    comment = item
                ) { }

                Box(modifier = Modifier.height(1.dp).fillMaxWidth())
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