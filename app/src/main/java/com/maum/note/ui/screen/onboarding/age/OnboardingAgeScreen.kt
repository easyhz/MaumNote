package com.maum.note.ui.screen.onboarding.age

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.picker.AgeNumberPicker
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.text.OnboardingText
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.util.picker.PickerState
import com.maum.note.core.designSystem.util.picker.rememberPickerState
import com.maum.note.core.model.note.AgeType
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeSideEffect
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

@Composable
fun OnboardingAgeScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingAgeViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pickerState = rememberPickerState(AgeType.MIXED)
    OnboardingAgeScreen(
        modifier = modifier,
        uiState = uiState,
        pickerState = pickerState,
        navigateUp = navigateUp,
        onClickNext = { viewModel.onClickNext(pickerState.selectedItem) },
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            OnboardingAgeSideEffect.NavigateToNext -> navigateToNext()
        }
    }
}

@Composable
private fun OnboardingAgeScreen(
    modifier: Modifier = Modifier,
    uiState: OnboardingAgeState,
    pickerState: PickerState<AgeType> = rememberPickerState(AgeType.MIXED),
    navigateUp: () -> Unit,
    onClickNext: () -> Unit,
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
                onClick = onClickNext,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
        ) {
            OnboardingText(
                title = stringResource(R.string.onboarding_age_title),
                subTitle = stringResource(R.string.onboarding_age_sub_title),
            )

            AgeNumberPicker(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                state = pickerState
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingAgeScreenPreview() {
    OnboardingAgeScreen(
        uiState = OnboardingAgeState.init(),
        navigateUp = {},
        onClickNext = {}
    )
}