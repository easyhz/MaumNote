package com.maum.note.ui.screen.setting.age

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
import com.maum.note.ui.screen.setting.age.contract.SettingAgeSideEffect
import com.maum.note.ui.screen.setting.age.contract.SettingAgeState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

@Composable
fun SettingAgeScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingAgeViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pickerState = rememberPickerState(AgeType.MIXED)
    SettingAgeScreen(
        modifier = modifier,
        uiState = uiState,
        pickerState = pickerState,
        navigateUp = navigateUp,
        onClickNext = { viewModel.onClickNext(pickerState.selectedItem) },
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is SettingAgeSideEffect.SetPickerAge -> {
                pickerState.animateToItem(sideEffect.ageType, AgeType.entries)
            }
            SettingAgeSideEffect.NavigateToNext -> navigateToNext()
        }
    }
}

@Composable
private fun SettingAgeScreen(
    modifier: Modifier = Modifier,
    uiState: SettingAgeState,
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
                text = stringResource(R.string.setting_age_button),
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
                title = stringResource(R.string.setting_age_title),
                subTitle = stringResource(R.string.setting_age_sub_title),
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
private fun SettingAgeScreenPreview() {
    SettingAgeScreen(
        uiState = SettingAgeState.init(),
        navigateUp = {},
        onClickNext = {}
    )
}