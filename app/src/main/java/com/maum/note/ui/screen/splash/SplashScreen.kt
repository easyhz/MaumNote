package com.maum.note.ui.screen.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.dialog.BasicDialog
import com.maum.note.core.designSystem.component.progress.ProgressBar
import com.maum.note.ui.screen.splash.contract.SplashSideEffect
import com.maum.note.ui.screen.splash.contract.SplashState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.SubText
import androidx.core.net.toUri

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    SplashScreen(
        modifier = modifier,
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateToOnboarding -> navigateToOnboarding()
            is SplashSideEffect.NavigateToHome -> navigateToHome()
            is SplashSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, sideEffect.url.toUri())
                context.startActivity(intent)
            }

            is SplashSideEffect.NavigateUp -> {
                (context as Activity).finish()
            }
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

        uiState.synchronizeState?.let {
            Column(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.synchronization, (uiState.synchronizeState * 100).toInt()
                    ),
                    style = AppTypography.body1_regular.copy(
                        color = MainText
                    )
                )
                ProgressBar(
                    progress = 0.3f
                )
            }
        }


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

    uiState.dialogMessage?.let { dialog ->
        BasicDialog(
            title = dialog.title,
            content = dialog.message,
            positiveButton = dialog.positiveButton,
            negativeButton = dialog.negativeButton
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        uiState = SplashState.init().copy(
            synchronizeState = 0.4f
        )
    )
}