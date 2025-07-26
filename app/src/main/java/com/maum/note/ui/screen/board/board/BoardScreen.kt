package com.maum.note.ui.screen.board.board

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.ad.BoardAdSection
import com.maum.note.core.designSystem.component.section.board.PostSection
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.model.board.Post
import com.maum.note.core.model.setting.BoardAdContent
import com.maum.note.ui.screen.board.board.contract.BoardSideEffect
import com.maum.note.ui.screen.board.board.contract.BoardState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.White
import java.time.LocalDateTime
import androidx.core.net.toUri
import com.maum.note.core.designSystem.component.button.BoardFloatingActionButton

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

@Composable
fun BoardScreen(
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    navigateToCreation: () -> Unit,
    navigateToPostDetail: (id: String, title: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    BoardScreen(
        modifier = modifier,
        uiState = uiState,
        onClickSetting = navigateToSetting,
        onClickFab = navigateToCreation,
        onClickAd = viewModel::onClickAd,
        onClickPost = {
            navigateToPostDetail(it.id, it.title)
        }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is BoardSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, sideEffect.url.toUri())
                context.startActivity(intent)
            }
        }
    }
}

@Composable
private fun BoardScreen(
    modifier: Modifier = Modifier,
    uiState: BoardState,
    onClickSetting: () -> Unit = { },
    onClickFab: () -> Unit = { },
    onClickAd: (BoardAdContent) -> Unit = { },
    onClickPost: (Post) -> Unit = { },
) {
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
                onClickSetting = onClickSetting
            )
        },
        floatingActionButton = {
            BoardFloatingActionButton(
                onClick = onClickFab
            )
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
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                BoardAdSection(
                    boardAdContents = uiState.configuration.boardAdContents,
                    onClick = onClickAd
                )
            }
            items(uiState.postList) { item ->
                PostSection(
                    post = item,
                    onClick = {
                        onClickPost(item)
                    }
                )
                Box(modifier = Modifier.height(8.dp).fillMaxWidth())
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