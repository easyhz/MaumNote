package com.maum.note.ui.screen.note.creation.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.maum.note.core.designSystem.component.bottomSheet.SentenceCountBottomSheet
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.SelectionSection
import com.maum.note.core.designSystem.component.textField.ContentTextFieldWithTitle
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentState
import com.maum.note.ui.theme.Primary

/**
 * Date: 2025. 5. 5.
 * Time: 오후 3:43
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteContentScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteContentViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    NoteContentScreen(
        modifier = modifier,
        uiState = uiState,
        scrollState = scrollState,
        navigateUp = navigateUp,
        onValueChange = viewModel::onInputValueChange,
        onClickSentenceCount = viewModel::onClickSentenceCount,
        onClickNext = viewModel::onClickNext,
        onDismissRequestSentenceBottomSheet = viewModel::onDismissRequestSentenceBottomSheet,
        onClickSentenceBottomSheetItem = viewModel::onClickSentenceBottomSheetItem,
        clearFocus = focusManager::clearFocus
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
//        when (sideEffect) {
//
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteContentScreen(
    modifier: Modifier = Modifier,
    uiState: NoteContentState,
    scrollState: LazyListState = rememberLazyListState(),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    navigateUp: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    onClickSentenceCount: () -> Unit,
    onClickNext: () -> Unit,
    onDismissRequestSentenceBottomSheet: () -> Unit = { onClickSentenceCount() },
    onClickSentenceBottomSheetItem: (SentenceType) -> Unit,
    clearFocus: () -> Unit,
) {
    AppScaffold(
        modifier = modifier.noRippleClickable { clearFocus() },
        topBar = {
            TopBar(
                leftContent = {
                    TopBarIcon(
                        modifier = it,
                        painter = painterResource(id = R.drawable.ic_arrow_left_leading),
                        alignment = Alignment.CenterStart,
                        onClick = navigateUp
                    )
                },
                centerContent = {
                    uiState.noteType?.inputTitle?.let { title ->
                        TopBarText(
                            modifier = it,
                            text = stringResource(id = title),
                            alignment = Alignment.Center,
                        )
                    }
                },
                rightContent = {
                    AnimatedVisibility(
                        modifier = it,
                        visible = uiState.isShowNext,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        TopBarText(
                            text = stringResource(id = R.string.note_content_create),
                            alignment = Alignment.CenterEnd,
                            color = Primary,
                            onClick = onClickNext
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SelectionSection(
                    title = stringResource(R.string.note_content_sentence_count),
                    value = stringResource(uiState.selectedSentenceType.title),
                    onClick = onClickSentenceCount
                )
            }

            item {
                ContentTextFieldWithTitle(
                    title = stringResource(R.string.note_content_input),
                    value = uiState.inputText,
                    onValueChange = onValueChange,
                    placeholder = uiState.noteType?.inputPlaceholder?.let { stringResource(it) }
                        ?: "",
                    maxCount = uiState.maxCount,
                    caption = if (uiState.noteType == NoteType.ANNOUNCEMENT_CONTENT) null else stringResource(
                        R.string.note_type_common_hint
                    ),
                    hint = uiState.noteType?.hint?.let { stringResource(it) },
                )
            }
        }
        if (uiState.isShowSentenceCountBottomSheet) {
            SentenceCountBottomSheet(
                modifier = Modifier,
                sheetState = sheetState,
                onDismissRequest = onDismissRequestSentenceBottomSheet,
                onClick = onClickSentenceBottomSheetItem,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NoteContentScreenPreview() {
    NoteContentScreen(
        uiState = NoteContentState.init().copy(
            noteType = NoteType.LETTER_GREETING
        ),
        navigateUp = { },
        onValueChange = { },
        onClickSentenceCount = { },
        onClickNext = { },
        onClickSentenceBottomSheetItem = { },
        onDismissRequestSentenceBottomSheet = { },
        clearFocus = { },
    )
}