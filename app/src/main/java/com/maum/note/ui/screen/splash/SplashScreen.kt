package com.maum.note.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.ui.screen.splash.contract.SplashSideEffect
import com.maum.note.ui.screen.splash.contract.SplashState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.SubText

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SplashScreen(
        modifier = modifier,
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            is SplashSideEffect.NavigateToOnboarding -> navigateToOnboarding()
            is SplashSideEffect.NavigateToMain -> navigateToMain()
        }
    }
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp),
            painter = painterResource(
                id = R.drawable.ic_app_icon
            ),
            contentDescription = "logo",
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            text = uiState.version,
            style = AppTypography.body2_regular.copy(
                color = SubText
            )
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        uiState = SplashState.init()
    )
}