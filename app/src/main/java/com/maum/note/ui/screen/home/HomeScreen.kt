package com.maum.note.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.HomeFloatingActionButton
import com.maum.note.core.designSystem.component.card.NoteCard
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.model.note.Note
import com.maum.note.ui.screen.home.contract.HomeState

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (Note) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onClickSetting = navigateToSetting,
        navigateToCreation = navigateToCreation,
        navigateToDetail = navigateToDetail
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        TODO("Not yet implemented")
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    onClickSetting: () -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (Note) -> Unit,
) {

    AppScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onClickSetting = onClickSetting
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton { navigateToCreation() }
        }
    ) { innerPadding ->
        if (uiState.noteList.isEmpty()) {
            EmptyView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
            )
        }
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
                    content = it.result,
                    date = it.createdAt.toLocalDate().toString(),
                    onClick = {
                        navigateToDetail(it)
                    },
                    onClickCopy = {

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
        uiState = HomeState.init(),
        onClickSetting = { },
        navigateToCreation = { },
        navigateToDetail = {  }
    )
}