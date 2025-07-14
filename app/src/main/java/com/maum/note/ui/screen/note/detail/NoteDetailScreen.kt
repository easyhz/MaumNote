package com.maum.note.ui.screen.note.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.common.util.date.toDisplayDate
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.section.DetailSection
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.model.note.NoteDetailType
import com.maum.note.core.model.note.NoteType
import com.maum.note.ui.screen.note.detail.contract.NoteDetailSideEffect
import com.maum.note.ui.screen.note.detail.contract.NoteDetailState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.LocalSnackBarHostState

/**
 * Date: 2025. 4. 19.
 * Time: 오후 11:24
 */

@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current
    val snackBarHost = LocalSnackBarHostState.current

    NoteDetailScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = navigateUp,
        onClickCopyButton = viewModel::onClickCopyButton
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is NoteDetailSideEffect.NavigateUp -> navigateUp()
            is NoteDetailSideEffect.CopyToClipboard -> {
                clipboardManager.setText(AnnotatedString(sideEffect.text))
            }
            is NoteDetailSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(
                    message = sideEffect.message
                )
            }
        }

    }
}

@Composable
private fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    uiState: NoteDetailState,
    navigateUp: () -> Unit,
    onClickCopyButton: () -> Unit,
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
                centerContent = {
                    TopBarText(
                        modifier = it,
                        text = stringResource(R.string.note_detail_title),
                        alignment = Alignment.Center,
                    )
                }
            )
        },
        bottomBar = {
            MainButton(
                text = stringResource(R.string.note_detail_copy_button),
                onClick = onClickCopyButton,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        ) {
            item {
                Text(
                    text = stringResource(R.string.note_detail_date, uiState.date.toDisplayDate()),
                    style = AppTypography.body1,
                )
            }
            items(uiState.detailContent) { note ->
                DetailSection(
                    title = stringResource(note.title),
                    value = note.content,
                    enabled = note.isCopyable,
                    minHeight = note.minHeight.dp,
                )
            }
            item {
                Box(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun NoteDetailScreenPreview() {
    NoteDetailScreen(
        uiState = NoteDetailState.init().copy(
            detailContent = NoteDetailType.entries(
                noteType = NoteType.PLAY_CONTEXT,
                typeValue = "안녕하세요.\uD83D\uDE0A\n" +
                        "\n" +
                        "'산에 피어도 꽃이고 들에 피어도 꽃이고 길가에 피어도 꽃이고 모두 다 꽃이야~\uD83C\uDFB5' 동요 [모두 다 꽃이야] 노래를 아시나요?\n" +
                        "꽃들은 색깔도, 모양도, 크기도 모두 저마다 조금씩 다릅니다. 이름 없이 피어도, 아무 데나 피어도, 생긴 대로 피어도 모두 한 송이의 꽃이지요.\uD83C\uDF39\n" +
                        "다솜이들도 마찬가지입니다. 모두가 자신만의 색깔로, 서로 다르더라도 함께 살아갈 때 아이들은 함께 사는 삶의 가치를 배우게 될 것입니다.\uD83D\uDE0E\n" +
                        "\n" +
                        "다솜반 교실에는 어느덧 다솜이들의 웃음소리가 가득합니다. 가만히 귀를 기울이면, 작고 소중한 세상들이 서로 만나 더 큰 세상을 만들어 나가고 있지요.\uD83E\uDD70 다솜이들 사이에 다툼이 없을 수는 없습니다. 양보가 어렵고, 마음을 말로 예쁘게 표현하기는 더 어려우니까요. 다솜이들이 친구를 대하기 어려워한다면, 어떻게 표현하면 좋을지 함께 여러 상황을 두고 연습해 보세요. 부모님께서는 다솜이들의 친구가 될 기회가 되고 다솜이들에게는 중요한 연습이 될 것입니다.\uD83D\uDC4F",
                ageValue = "324",
                sentenceValue = "Sample Sentence",
                inputValue = "Sample Input",
            )
        ),
        navigateUp = { },
        onClickCopyButton = { }
    )
}