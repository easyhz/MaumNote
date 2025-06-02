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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.common.util.date.toDisplayTimeAgo
import com.maum.note.core.designSystem.component.button.HomeFloatingActionButton
import com.maum.note.core.designSystem.component.card.NoteCard
import com.maum.note.core.designSystem.component.empty.EmptyView
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.topbar.HomeTopBar
import com.maum.note.core.designSystem.util.notification.CheckNotification
import com.maum.note.core.model.note.Note
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import com.maum.note.ui.theme.LocalDateTimeFormatter
import com.maum.note.ui.theme.LocalSnackBarHostState
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
    val clipboardManager = LocalClipboardManager.current
    val snackBarHost = LocalSnackBarHostState.current

    CheckNotification(
        needNotificationPermission = uiState.needNotificationPermission,
        action = viewModel::updatePushNotificationStatus
    )

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onClickSetting = navigateToSetting,
        navigateToCreation = navigateToCreation,
        navigateToDetail = navigateToDetail,
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
    appDateTimeFormatter: AppDateTimeFormatter = LocalDateTimeFormatter.current,
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
                onClickSetting = onClickSetting
            )
        },
        floatingActionButton = {
            HomeFloatingActionButton { navigateToCreation() }
        }
    ) { innerPadding ->
        if (!uiState.isLoading && uiState.noteList.isEmpty()) {
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
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 80.dp)
        ) {
            items(uiState.noteList) {
                NoteCard(
                    modifier = Modifier
                        .height(196.dp),
                    content = it.result,
                    date = it.createdAt.toDisplayTimeAgo(context = context, appDateTimeFormatter = appDateTimeFormatter),
                    onClick = {
                        navigateToDetail(it)
                    },
                    onClickCopy = {
                        onClickCopy(it)
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
        uiState = HomeState.init().copy(
            noteList = listOf(
                Note(
                    id = 1L,
                    noteType = com.maum.note.core.model.note.NoteType.DEFAULT,
                    ageType = com.maum.note.core.model.note.AgeType.MIXED,
                    sentenceCountType = com.maum.note.core.model.note.SentenceType.TWO_TO_THREE,
                    inputContent = "input content",
                    result = "result content",
                    createdAt = LocalDateTime.now().minusMinutes(1),
                )
            )
        ),
        appDateTimeFormatter = AppDateTimeFormatter(),
        onClickSetting = { },
        navigateToCreation = { },
        navigateToDetail = { },
        onClickCopy = { },
    )
}