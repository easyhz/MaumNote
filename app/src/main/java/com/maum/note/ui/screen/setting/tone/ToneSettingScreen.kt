package com.maum.note.ui.screen.setting.tone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.model.note.NoteType
import com.maum.note.ui.screen.setting.tone.component.ToneTextField
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary

/**
 * Date: 2025. 4. 19.
 * Time: 오전 11:21
 */

@Composable
fun ToneSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ToneSettingViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    ToneSettingScreen(
        modifier = modifier,
        uiState = uiState,
        scrollState = scrollState,
        navigateUp = navigateUp,
        onValueChange = { type, value ->
            viewModel.onContentValueChange(noteType = type, newValue = value)
        },
        clearFocus = focusManager::clearFocus,
        onClickSave = viewModel::onClickSave
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
    }
}

@Composable
private fun ToneSettingScreen(
    modifier: Modifier = Modifier,
    uiState: ToneSettingState,
    scrollState: LazyListState = rememberLazyListState(),
    navigateUp: () -> Unit,
    onValueChange: (NoteType, TextFieldValue) -> Unit,
    clearFocus: () -> Unit,
    onClickSave: () -> Unit,
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
                    Text(
                        modifier = it,
                        text = stringResource(R.string.setting_note_tone),
                        style = AppTypography.heading5_semiBold,
                        color = MainText
                    )
                },
                rightContent = {
                    TopBarText(
                        modifier = it,
                        text = stringResource(R.string.setting_note_tone_save),
                        alignment = Alignment.CenterEnd,
                        color = Primary,
                        onClick = onClickSave
                    )
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            state = scrollState,
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 92.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(NoteType.entries) { type ->
                ToneTextField(
                    modifier = Modifier.padding(bottom = 20.dp),
                    noteType = type,
                    value = uiState.contents[type] ?: TextFieldValue(""),
                    onValueChange = { value ->
                        onValueChange(type, value)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToneSettingScreenPreview() {
    ToneSettingScreen(
        uiState = ToneSettingState.init(),
        navigateUp = {  },
        onValueChange = { _, _ -> },
        clearFocus = {  },
        onClickSave = {  },
    )
}