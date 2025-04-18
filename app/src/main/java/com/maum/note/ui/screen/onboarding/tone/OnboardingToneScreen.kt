package com.maum.note.ui.screen.onboarding.tone

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
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
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.text.OnboardingText
import com.maum.note.core.designSystem.component.textField.ContentTextField
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.screen.onboarding.tone.contract.OnboardingToneState
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.AppTypography

/**
 * Date: 2025. 4. 18.
 * Time: 오후 3:37
 */

@Composable
fun OnboardingToneScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingToneViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    onClickNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    LaunchedEffect(imeVisible) {
        viewModel.onImeVisibilityChanged(imeVisible)
    }

    OnboardingToneScreen(
        modifier = modifier,
        uiState = uiState,
        scrollState = scrollState,
        navigateUp = navigateUp,
        onClickNext = onClickNext,
        onValueChange = viewModel::onContentValueChange,
        clearFocus = focusManager::clearFocus
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            else -> { }
        }
    }
}

@Composable
private fun OnboardingToneScreen(
    modifier: Modifier = Modifier,
    uiState: OnboardingToneState,
    scrollState: ScrollState = rememberScrollState(),
    navigateUp: () -> Unit,
    onClickNext: () -> Unit,
    onValueChange: (TextFieldValue) -> Unit,
    clearFocus: () -> Unit,
) {
    AppScaffold(
        modifier = modifier,
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
            )
        },
        bottomBar = {
            MainButton(
                text = stringResource(R.string.note_creation_note_type_selection_button),
                isImeVisible = uiState.isImeVisible,
                onClick = onClickNext,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .noRippleClickable { clearFocus() }
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OnboardingText(
                title = stringResource(R.string.onboarding_tone_title),
                subTitle = stringResource(R.string.onboarding_tone_sub_title),
            )

            ContentTextField(
                value = uiState.content,
                onValueChange = onValueChange,
                placeholder = stringResource(R.string.onboarding_tone_placeholder),
            )

            Text(
                text = stringResource(R.string.onboarding_tone_description),
                style = AppTypography.body3,
                color = SubText
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingToneScreenPreview() {
    OnboardingToneScreen(
        uiState = OnboardingToneState.init(),
        navigateUp = { },
        onClickNext = { },
        onValueChange = {  },
        clearFocus = {  },
    )
}