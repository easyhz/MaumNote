package com.maum.note.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.common.util.date.toDisplayTimeAgo
import com.maum.note.core.designSystem.component.button.HomeFloatingActionButton
import com.maum.note.core.designSystem.component.card.note.NoteCard
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.error.ErrorView
import com.maum.note.core.designSystem.component.loading.FullLoadingIndicator
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.designSystem.util.notification.CheckNotification
import com.maum.note.core.model.note.Note
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import com.maum.note.ui.theme.LocalSnackBarHostState
import com.maum.note.ui.theme.NoteBackground
import com.maum.note.ui.theme.Primary
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notes = viewModel.notesFlow.collectAsLazyPagingItems()
    val clipboardManager = LocalClipboardManager.current
    val snackBarHost = LocalSnackBarHostState.current

    CheckNotification(
        needNotificationPermission = uiState.needNotificationPermission,
        action = viewModel::updatePushNotificationStatus
    )

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        notes = notes,
        onClickSetting = navigateToSetting,
        navigateToCreation = navigateToCreation,
        navigateToDetail = {
            viewModel.logEventNoteSelected()
            navigateToDetail(it)
        },
        onClickCopy = viewModel::onClickCopyButton
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
        }
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    notes: LazyPagingItems<Note>,
    onClickSetting: () -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (Note) -> Unit,
    onClickCopy: (Note) -> Unit,
) {
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
                    ErrorView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f),
                        onClick = notes::retry
                    )
                }

                states.isEmpty -> {
                    EmptyView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier.padding(innerPadding),
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
                        items(notes.itemCount, key = notes.itemKey()) { index ->
                            notes[index]?.let { note ->
                                NoteCard(
                                    modifier = Modifier
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Primary,
                                        trackColor = NoteBackground,
                                    )
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
    )
}