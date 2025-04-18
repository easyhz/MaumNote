package com.maum.note.ui.screen.note.creation.select

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.card.NoteTypeCard
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.model.note.NoteType
import com.maum.note.ui.screen.note.creation.select.contract.NoteTypeSelectionState
import com.maum.note.ui.theme.AppTypography

/**
 * Date: 2025. 4. 18.
 * Time: 오후 1:39
 */

@Composable
fun NoteTypeSelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteTypeSelectionViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteTypeSelectionScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = navigateUp,
        onSelectNoteType = viewModel::selectNoteType,
        onClickNext = navigateToNext
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
    }
}

@Composable
private fun NoteTypeSelectionScreen(
    modifier: Modifier = Modifier,
    uiState: NoteTypeSelectionState,
    navigateUp: () -> Unit,
    onSelectNoteType: (NoteType) -> Unit,
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
            AnimatedVisibility(
                visible = uiState.selectedNoteType != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MainButton(
                    modifier = Modifier.padding(20.dp),
                    text = stringResource(R.string.note_creation_note_type_selection_button),
                    onClick = onClickNext,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Text(
                text = stringResource(R.string.note_creation_note_type_selection_title),
                style = AppTypography.h1_semiBold.copy(
                    textAlign = TextAlign.Center
                )
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                NoteType.entries.forEach { noteType ->
                    NoteTypeCard(
                        noteType = noteType,
                        isChecked = uiState.selectedNoteType == noteType,
                        onClick = {
                            onSelectNoteType(noteType)
                        }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun NoteTypeSelectionScreenPreview() {
    NoteTypeSelectionScreen(
        uiState = NoteTypeSelectionState.init(),
        navigateUp = {},
        onSelectNoteType = {},
        onClickNext = { }
    )
}