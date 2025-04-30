package com.maum.note.ui.screen.onboarding.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.onboarding.OnboardingInformation
import com.maum.note.core.designSystem.component.onboarding.OnboardingInformationType
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.extension.modifier.dropShadow
import com.maum.note.ui.screen.onboarding.start.contract.OnboardingStartState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText

/**
 * Date: 2025. 4. 19.
 * Time: 오전 10:56
 */

@Composable
fun OnboardingStartScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingStartViewModel = hiltViewModel(),
    navigateToNext: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingStartScreen(
        modifier = modifier,
        uiState = uiState,
        onClickNext = navigateToNext
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")

    }
}

@Composable
private fun OnboardingStartScreen(
    modifier: Modifier = Modifier,
    uiState: OnboardingStartState,
    onClickNext: () -> Unit,
) {
    AppScaffold(
        bottomBar = {
            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.onboarding_start_button),
                onClick = onClickNext
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 28.dp)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(72.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon_large),
                    contentDescription = "",
                    modifier = Modifier
                        .size(72.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(14.dp),
                            blur = 10.dp,
                            color = Color.Black.copy(alpha = 0.25f),
                        )
                        .clip(RoundedCornerShape(14.dp))
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.onboarding_start_title),
                        style = AppTypography.heading1_bold,
                        color = MainText
                    )
                    Text(
                        text = stringResource(id = R.string.onboarding_start_sub_title),
                        style = AppTypography.heading4_semiBold,
                        color = MainText
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                OnboardingInformationType.entries.forEach { type ->
                    OnboardingInformation(
                        informationType = type
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingStartScreenPreview() {
    OnboardingStartScreen(
        uiState = OnboardingStartState.init(),
        onClickNext = {  }
    )
}