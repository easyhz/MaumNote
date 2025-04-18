package com.maum.note.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.card.NoteCard
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.ui.screen.home.contract.HomeState
import java.time.LocalDate

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        uiState = uiState
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
) {
    val context = LocalContext.current
    AppScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar {  }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(uiState.noteList) {
                NoteCard(
                    modifier = Modifier
                        .height(196.dp),
                    content = it,
                    date = LocalDate.now().toString(),
                    onClick = {
                        Toast.makeText(
                            context,
                            "Clicked on note: $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onClickCopy = {
                        Toast.makeText(
                            context,
                            "Copy on note: $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeState.init()
    )
}