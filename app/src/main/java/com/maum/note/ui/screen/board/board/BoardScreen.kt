package com.maum.note.ui.screen.board.board

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.maum.note.R
import com.maum.note.core.common.helper.page.isAppending
import com.maum.note.core.common.helper.page.isEmpty
import com.maum.note.core.common.helper.page.isInitialError
import com.maum.note.core.common.helper.page.isInitialLoading
import com.maum.note.core.common.helper.page.isRefreshing
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.BoardFloatingActionButton
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.error.ErrorView
import com.maum.note.core.designSystem.component.loading.BottomLoadingIndicator
import com.maum.note.core.designSystem.component.loading.FullLoadingIndicator
import com.maum.note.core.designSystem.component.loading.PullToRefreshIndicator
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
import com.maum.note.ui.theme.NoteBackground
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubBackground
import com.maum.note.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

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
    val posts = viewModel.postsFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    BoardScreen(
        modifier = modifier,
        uiState = uiState,
        posts = posts,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BoardScreen(
    modifier: Modifier = Modifier,
    uiState: BoardState,
    posts: LazyPagingItems<Post>,
    onClickSetting: () -> Unit = { },
    onClickFab: () -> Unit = { },
    onClickAd: (BoardAdContent) -> Unit = { },
    onClickPost: (Post) -> Unit = { },
) {
    val pullRefreshState = rememberPullToRefreshState()
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
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = posts.isRefreshing,
            onRefresh = posts::refresh,
            state = pullRefreshState,
            indicator = {
                PullToRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullRefreshState,
                    isRefreshing = posts.isRefreshing,
                )
            }
        ) {
            AnimatedContent(
                targetState = posts,
                label = "",
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { states ->
                when {
                    states.isInitialLoading -> {
                        FullLoadingIndicator(isLoading = true)
                    }

                    states.isInitialError -> {
                        Column {
                            BoardAdSection(
                                boardAdContents = uiState.configuration.boardAdContents,
                                onClick = onClickAd
                            )
                            ErrorView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f),
                                onClick = posts::retry
                            )
                        }
                    }

                    states.isEmpty -> {
                        Column {
                            BoardAdSection(
                                boardAdContents = uiState.configuration.boardAdContents,
                                onClick = onClickAd
                            )
                            EmptyView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f),
                                text = stringResource(R.string.board_empty)
                            )
                        }
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 80.dp)
                        ) {
                            item {
                                BoardAdSection(
                                    boardAdContents = uiState.configuration.boardAdContents,
                                    onClick = onClickAd
                                )
                            }
                            items(posts.itemCount, key = posts.itemKey()) { index ->
                                posts[index]?.let { post ->
                                    PostSection(
                                        modifier = Modifier.animateItem(),
                                        post = post,
                                        onClick = {
                                            onClickPost(post)
                                        }
                                    )
                                    Box(modifier = Modifier.height(8.dp).fillMaxWidth())
                                }
                            }
                            if (posts.isAppending) {
                                item {
                                    BottomLoadingIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BoardScreenPreview() {
    val pagingFlow: Flow<PagingData<Post>> = flowOf(PagingData.from(
        listOf(
            Post(
                id = "3",
                title = "title",
                content = "content",
                userNickname = "ㅇㅇ",
                userId = "sdf",
                isAnonymous = false,
                commentCount = 3,
                createdAt = LocalDateTime.now(),
            ), Post(
                id = "3",
                title = "title",
                content = "content",
                userNickname = "ㅇㅇ",
                userId = "sdf",
                isAnonymous = false,
                commentCount = 3,
                createdAt = LocalDateTime.now(),
            )
        )
    ))
    val posts = pagingFlow.collectAsLazyPagingItems()

    BoardScreen(
        uiState = BoardState.init(),
        posts = posts
    )
}