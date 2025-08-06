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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.bottomSheet.BottomSheetType
import com.maum.note.core.designSystem.component.bottomSheet.ListBottomSheet
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.loading.FullLoadingIndicator
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.board.CommentSection
import com.maum.note.core.designSystem.component.section.board.PostSection
import com.maum.note.core.designSystem.component.textField.CommentTextField
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.model.board.Comment
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailSideEffect
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import com.maum.note.ui.screen.board.post.detail.model.MoreBottomSheet
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
    navigateToBoard: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    PostDetailScreen(
        modifier = modifier,
        uiState = uiState,
        clearFocus = focusManager::clearFocus,
        navigateUp = navigateUp,
        onValueChange = viewModel::onCommentTextChanged,
        onClickPostMore = viewModel::onClickPostMore,
        onClickCommentMore = viewModel::onClickCommentMore,
        onClickAnonymous = viewModel::onClickAnonymous,
        onClickSend = viewModel::onClickSend,
        hideMoreBottomSheet = viewModel::hideMoreBottomSheet,
        onClickBottomSheetItem = viewModel::onClickBottomSheetItem
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is PostDetailSideEffect.NavigateUp -> navigateUp()
            is PostDetailSideEffect.NavigateToBoard -> navigateToBoard()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailScreen(
    modifier: Modifier = Modifier,
    uiState: PostDetailState,
    clearFocus: () -> Unit,
    navigateUp: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit = { },
    onClickPostMore: () -> Unit = { },
    onClickCommentMore: (Comment) -> Unit = { },
    onClickAnonymous: () -> Unit = { },
    onClickSend: () -> Unit = { },
    hideMoreBottomSheet: () -> Unit = { },
    onClickBottomSheetItem: (MoreBottomSheet, BottomSheetType) -> Unit = { _, _ -> },
) {
    val haptic = LocalHapticFeedback.current

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
                        onClick = onClickPostMore
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
                onClickSend = {
                    onClickSend()
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            item {
                if (uiState.post == null) return@item
                PostSection(
                    post = uiState.post,
                    onClick = null
                )
                Box(modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth())
            }

            items(uiState.comments) { item ->
                CommentSection(
                    modifier = Modifier.animateItem(),
                    comment = item,
                    onClickMore = { onClickCommentMore(item) }
                )

                Box(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth())
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

        if (uiState.moreBottomSheet != null) {
            ListBottomSheet(
                items = uiState.moreBottomSheet.list,
                title = stringResource(uiState.moreBottomSheet.title),
                onDismissRequest = hideMoreBottomSheet,
                onClick = { onClickBottomSheetItem(uiState.moreBottomSheet, it) }
            )
        }
    }

    FullLoadingIndicator(
        isLoading = uiState.isLoading
    )

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