package com.maum.note.ui.screen.board.creation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.maum.note.core.designSystem.component.button.AnonymousCheckButton
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.textField.ContentTextFieldWithTitle
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.designSystem.util.textField.TextFieldType
import com.maum.note.ui.screen.board.creation.contract.BoardCreationState
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.White

/**
 * Date: 2025. 7. 25.
 * Time: 오후 6:15
 */

@Composable
fun BoardCreationScreen(
    modifier: Modifier = Modifier,
    viewModel: BoardCreationViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    BoardCreationScreen(
        modifier = modifier,
        uiState = uiState,
        clearFocus = focusManager::clearFocus,
        navigateUp = navigateUp,
        onClickNext = viewModel::onClickNext,
        onValueChangeTitle = viewModel::onValueChangeTitle,
        onValueChangeContent = viewModel::onValueChangeContent,
        onClickAnonymous = viewModel::setIsAnonymous,
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
//        when(sideEffect) {
//
//        }
    }
}

@Composable
private fun BoardCreationScreen(
    modifier: Modifier = Modifier,
    uiState: BoardCreationState,
    clearFocus: () -> Unit,
    navigateUp: () -> Unit,
    onClickNext: () -> Unit,
    onValueChangeTitle: (TextFieldValue) -> Unit,
    onValueChangeContent: (TextFieldValue) -> Unit,
    onClickAnonymous: () -> Unit,
) {
    val scrollState = rememberScrollState()

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
                        text = stringResource(id = R.string.board_creation_title),
                        alignment = Alignment.Center,
                    )
                },
                rightContent = {
                    TopBarText(
                        modifier = it,
                        text = stringResource(id = R.string.board_creation_button),
                        alignment = Alignment.CenterEnd,
                        color = Primary,
                        onClick = onClickNext
                    )
                },
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .background(White)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                AnonymousCheckButton(
                    isChecked = uiState.isAnonymous,
                    onClick = onClickAnonymous,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ContentTextFieldWithTitle(
                title = stringResource(R.string.board_creation_post_title),
                value = uiState.titleText,
                onValueChange = onValueChangeTitle,
                textFieldType = TextFieldType.Single(),
                singleLine = true,
                placeholder = stringResource(R.string.board_creation_post_title_placeholder),
            )

            ContentTextFieldWithTitle(
                title = stringResource(R.string.board_creation_post_content),
                value = uiState.contentText,
                onValueChange = onValueChangeContent,
                textFieldType = TextFieldType.Multiple(
                    maxHeight = 200.dp
                ),
                caption = stringResource(R.string.board_creation_post_rule),
                placeholder = stringResource(R.string.board_creation_post_content_placeholder),
            )
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
private fun BoardCreationScreenPreview() {
    BoardCreationScreen(
        uiState = BoardCreationState.init(),
        clearFocus = { },
        navigateUp = { },
        onClickNext = { },
        onValueChangeTitle = { },
        onValueChangeContent = { },
        onClickAnonymous = { },
    )
}