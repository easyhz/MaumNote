package com.maum.note.ui.screen.home

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.maum.note.core.common.util.date.toDisplayTimeAgo
import com.maum.note.core.designSystem.component.button.HomeFloatingActionButton
import com.maum.note.core.designSystem.component.card.note.NoteCard
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.error.ErrorView
import com.maum.note.core.designSystem.component.loading.BottomLoadingIndicator
import com.maum.note.core.designSystem.component.loading.FullLoadingIndicator
import com.maum.note.core.designSystem.component.loading.PullToRefreshIndicator
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.ad.AdSection
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.designSystem.util.notification.CheckNotification
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.setting.AdContent
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import com.maum.note.ui.theme.LocalSnackBarHostState
import com.maum.note.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

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
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notes = viewModel.notesFlow.collectAsLazyPagingItems()
    val clipboardManager = LocalClipboardManager.current
    val snackBarHost = LocalSnackBarHostState.current
    val state: LazyGridState = rememberLazyGridState()

    CheckNotification(
        needNotificationPermission = uiState.needNotificationPermission,
        action = viewModel::updatePushNotificationStatus
    )

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        notes = notes,
        state = state,
        onClickSetting = navigateToSetting,
        navigateToCreation = navigateToCreation,
        navigateToDetail = {
            viewModel.logEventNoteSelected()
            navigateToDetail(it)
        },
        onClickCopy = viewModel::onClickCopyButton,
        onClickAd = { }
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.message,
                )
            }

            is HomeSideEffect.CopyToClipboard -> {
                clipboardManager.setText(AnnotatedString(sideEffect.result))
            }

            is HomeSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, sideEffect.url.toUri())
                context.startActivity(intent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    notes: LazyPagingItems<Note>,
    state: LazyGridState = rememberLazyGridState(),
    onClickSetting: () -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (Note) -> Unit,
    onClickCopy: (Note) -> Unit,
    onClickAd: (AdContent) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    AppScaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                content = {
                    Image(
                        modifier = Modifier.height(40.dp),
                        painter = painterResource(R.drawable.ic_app_icon),
                        contentDescription = null
                    )
                },
                onClickSetting = onClickSetting
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton { navigateToCreation() }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = notes.isRefreshing,
            onRefresh = notes::refresh,
            state = pullRefreshState,
            indicator = {
                PullToRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullRefreshState,
                    isRefreshing = notes.isRefreshing,
                )
            }
        ) {
            AnimatedContent(
                targetState = notes,
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
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            HomeAdContent(
                                adContents = uiState.configuration.adContents,
                                onClick = onClickAd
                            )
                            ErrorView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f),
                                onClick = notes::retry
                            )
                        }
                    }

                    states.isEmpty -> {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            HomeAdContent(
                                adContents = uiState.configuration.adContents,
                                onClick = onClickAd
                            )
                            EmptyView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f)
                            )
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
                            state = state,
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                end = 12.dp,
                                top = 8.dp,
                                bottom = 80.dp
                            )
                        ) {
                            item(span = { GridItemSpan(2)} ) {
                                HomeAdContent(
                                    adContents = uiState.configuration.adContents,
                                    onClick = onClickAd
                                )
                            }
                            items(notes.itemCount, key = notes.itemKey()) { index ->
                                notes[index]?.let { note ->
                                    NoteCard(
                                        modifier = Modifier
                                            .animateItem()
                                            .height(196.dp),
                                        content = note.result,
                                        date = note.createdAt.toDisplayTimeAgo(context = context),
                                        onClick = {
                                            navigateToDetail(note)
                                        },
                                        onClickCopy = {
                                            onClickCopy(note)
                                        }
                                    )
                                }
                            }

                            if (notes.isAppending) {
                                item(span = { GridItemSpan(2) }) {
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

@Composable
private fun HomeAdContent(
    modifier: Modifier = Modifier,
    adContents: List<AdContent>,
    onClick: (AdContent) -> Unit
) {
    AdSection(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        adContents = adContents,
        height = 68.dp,
        placeholderColor = White,
        onClick = onClick
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val emptyPagingFlow: Flow<PagingData<Note>> = flowOf(PagingData.empty())
    val notes = emptyPagingFlow.collectAsLazyPagingItems()

    HomeScreen(
        uiState = HomeState.init().copy(
            noteList = listOf(
                Note(
                    id = "12312",
                    noteType = com.maum.note.core.model.note.NoteType.DEFAULT,
                    ageType = com.maum.note.core.model.note.AgeType.MIXED,
                    sentenceCountType = com.maum.note.core.model.note.SentenceType.TWO_TO_THREE,
                    inputContent = "input content",
                    result = "result content",
                    createdAt = LocalDateTime.now().minusMinutes(1),
                )
            )
        ),
        notes = notes,
        onClickSetting = { },
        navigateToCreation = { },
        navigateToDetail = { },
        onClickCopy = { },
        onClickAd = { }
    )
}