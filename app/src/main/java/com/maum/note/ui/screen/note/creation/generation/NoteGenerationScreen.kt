package com.maum.note.ui.screen.note.creation.generation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.loading.LoadingIndicator
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.ui.screen.note.creation.generation.contract.NoteGenerationState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.Primary

/**
 * Date: 2025. 5. 5.
 * Time: 오후 6:41
 */

@Composable
fun NoteGenerationScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteGenerationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteGenerationScreen(
        modifier = modifier,
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
    }
}

@Composable
private fun NoteGenerationScreen(
    modifier: Modifier = Modifier,
    uiState: NoteGenerationState,
) {
    AppScaffold(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                Box(modifier = Modifier.weight(0.7f))
                AnimatedContent(
                    targetState = uiState.currentTextIndex,
                    transitionSpec = getTextTransition(),
                    contentAlignment = Alignment.Center,
                    label = ""
                ) { state ->
                    Text(
                        text = stringResource(uiState.captionTexts[state]),
                        style = AppTypography.body1.copy(
                            color = Primary
                        )
                    )
                }
                LoadingIndicator(
                    isLoading = true,
                    width = 180.dp,
                    height = 80.dp
                )
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

private fun <T> getTextTransition(): AnimatedContentTransitionScope<T>.() -> ContentTransform {
    val duration = 500
    return {
        (slideInVertically(initialOffsetY = { it * 2 }) +
                fadeIn(animationSpec = tween(durationMillis = duration)))
            .togetherWith(
                slideOutVertically(targetOffsetY = { -it * 2 }) +
                        fadeOut(animationSpec = tween(durationMillis = duration))
            )
    }
}

@Preview
@Composable
private fun NoteGenerationScreenPreview() {
    NoteGenerationScreen(
        uiState = NoteGenerationState.init()
    )
}