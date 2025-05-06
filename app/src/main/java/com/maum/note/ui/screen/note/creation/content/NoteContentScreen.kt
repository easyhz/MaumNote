package com.maum.note.ui.screen.note.creation.content

import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.bottomSheet.SentenceCountBottomSheet
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.SelectionSection
import com.maum.note.core.designSystem.component.textField.ContentTextFieldWithTitle
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.model.error.ErrorMessage
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.core.model.note.generation.GenerationNote
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentSideEffect
import com.maum.note.ui.screen.note.creation.content.contract.NoteContentState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
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
    errorMessage: ErrorMessage?,
    navigateUp: () -> Unit,
    navigateToNext: (GenerationNote) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(errorMessage) {
        viewModel.setErrorMessage(errorMessage)
    }

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
        clearFocus = focusManager::clearFocus,
        onGloballyPosition = viewModel::onGloballyPositioned,
        changeFocus = viewModel::onChangeFocus,
        setTextLayoutResult = viewModel::setLayoutResult,
    )

    uiState.errorMessage?.let { error ->
        BasicDialog(
            title = error.title ?: stringResource(R.string.error_title),
            content = error.message,
            positiveButton = BasicDialogButton(
                text = stringResource(R.string.dialog_positive_button),
                backgroundColor = Primary,
                style = AppTypography.heading5_semiBold.copy(color = Color.White),
                onClick = {
                    viewModel.setErrorMessage(null)
                }
            ),
            negativeButton = null
        )
    }

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NoteContentSideEffect.NavigateUp -> navigateUp()
            is NoteContentSideEffect.NavigateToNext -> {
                navigateToNext(sideEffect.generationNote)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteContentScreen(
    modifier: Modifier = Modifier,
    uiState: NoteContentState,
    scrollState: ScrollState = rememberScrollState(),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    navigateUp: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    onClickSentenceCount: () -> Unit,
    onClickNext: () -> Unit,
    onDismissRequestSentenceBottomSheet: () -> Unit = { onClickSentenceCount() },
    onClickSentenceBottomSheetItem: (SentenceType) -> Unit,
    clearFocus: () -> Unit,
    onGloballyPosition: (View) -> Unit = { },
    changeFocus: (Boolean) -> Unit = { },
    setTextLayoutResult: (TextLayoutResult) -> Unit = { },
) {
    val focusRequester = remember { FocusRequester() }
    val halfHeight = LocalConfiguration.current.screenHeightDp / 2
    val view = LocalView.current
    val density = LocalDensity.current
    val paddingHeight = remember(density) { with(density) { 100.dp.toPx().toInt() } }

    LaunchedEffect(key1 = uiState.absoluteCursorY, key2 = uiState.isFocused) {
        val targetScroll = uiState.absoluteCursorY - halfHeight - paddingHeight

        val shouldAnimateScroll = !uiState.isMoved &&
                (uiState.isFocused || scrollState.maxValue >= halfHeight) &&
                scrollState.value != targetScroll

        if (shouldAnimateScroll) {
            scrollState.animateScrollTo(targetScroll)
        }
    }

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
                    uiState.noteType?.inputTitle?.let { title ->
                        TopBarText(
                            modifier = it,
                            text = stringResource(id = title),
                            alignment = Alignment.Center,
                        )
                    }
                },
                rightContent = {
                    TopBarText(
                        modifier = it,
                        text = stringResource(id = R.string.note_content_create),
                        alignment = Alignment.CenterEnd,
                        color = Primary,
                        onClick = onClickNext
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectionSection(
                title = stringResource(R.string.note_content_sentence_count),
                value = stringResource(uiState.selectedSentenceType.title),
                onClick = onClickSentenceCount
            )

            ContentTextFieldWithTitle(
                modifier = Modifier.imePadding(),
                textFieldModifier = Modifier
                    .focusRequester(focusRequester)
                    .onGloballyPositioned { _ ->
                        onGloballyPosition(view)
                    }
                    .onFocusEvent {
                        changeFocus(it.hasFocus)
                    }.onKeyEvent {
                        it.key == Key.Enter
                    },
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
                onTextLayout = { result ->
                    setTextLayoutResult(result)
                }
            )
        }

        if (uiState.isShowSentenceCountBottomSheet) {
            SentenceCountBottomSheet(
                modifier = Modifier,
                sheetState = sheetState,
                onDismissRequest = onDismissRequestSentenceBottomSheet,
                onClick = onClickSentenceBottomSheetItem,
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