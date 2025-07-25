package com.maum.note.ui.screen.board.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.board.PostSection
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.model.board.Post
import com.maum.note.ui.screen.board.board.contract.BoardState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.White
import java.time.LocalDateTime

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

@Composable
fun BoardScreen(
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoardScreen(
        modifier = modifier,
        uiState = uiState
    )

//    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
//        TODO("Not yet implemented")
//        when (sideEffect) {
//
//        }
//    }
}

@Composable
private fun BoardScreen(
    modifier: Modifier = Modifier,
    uiState: BoardState,
) {

    val context = LocalContext.current
    AppScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                modifier = Modifier.background(White),
                content = {
                    Text(
                        text = stringResource(id = R.string.board),
                        style = AppTypography.heading3_semiBold.copy(
                            color = MainText
                        )
                    )
                },
                onClickSetting = { }
            )
        },
        floatingActionButton = {
//            HomeFloatingActionButton { navigateToCreation() }
        }
    ) { innerPadding ->
        if (!uiState.isLoading && uiState.postList.isEmpty()) {
            EmptyView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
            )
        }
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(uiState.postList) { item ->
                PostSection(
                    post = item
                ) { }
            }
        }
    }

}

@Preview
@Composable
private fun BoardScreenPreview() {
    BoardScreen(
        uiState = BoardState.init().copy(
            postList = listOf(
                Post(
                    id = "3",
                    title = "title",
                    content = "content",
                    author = "author",
                    isAnonymous = false,
                    commentCount = 3,
                    createdAt = LocalDateTime.now(),
                ), Post(
                    id = "3",
                    title = "title",
                    content = "content",
                    author = "author",
                    isAnonymous = false,
                    commentCount = 3,
                    createdAt = LocalDateTime.now(),
                )
            )
        )
    )
}