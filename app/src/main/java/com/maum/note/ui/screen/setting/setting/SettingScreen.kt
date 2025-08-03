package com.maum.note.ui.screen.setting.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.setting.SettingItemView
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.model.setting.MajorSettingItem
import com.maum.note.core.model.setting.SettingItem
import com.maum.note.ui.screen.setting.setting.contract.SettingSideEffect
import com.maum.note.ui.screen.setting.setting.contract.SettingState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToToneSetting: () -> Unit,
    navigateToAgeSetting: () -> Unit,
    navigateToProfileSetting: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    SettingScreen(
        modifier = modifier,
        uiState = uiState,
        navigateUp = navigateUp,
        onClickItem = viewModel::onClickItem
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            SettingSideEffect.NavigateToToneSetting -> navigateToToneSetting()
            SettingSideEffect.NavigateToAgeSetting -> navigateToAgeSetting()
            SettingSideEffect.NavigateToProfileSetting -> navigateToProfileSetting()
            is SettingSideEffect.NavigateToUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                context.startActivity(intent)
            }
        }
    }
}

@Composable
private fun SettingScreen(
    modifier: Modifier = Modifier,
    uiState: SettingState,
    navigateUp: () -> Unit,
    onClickItem: (SettingItem) -> Unit,
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
                    Text(
                        modifier = it,
                        text = stringResource(R.string.setting),
                        style = AppTypography.heading5_semiBold,
                        color = MainText
                    )
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        ) {
            items(MajorSettingItem.entries) { major ->
                SettingItemView(
                    majorSettingItem = major,
                    onClickItem = onClickItem,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        uiState = SettingState.init(),
        navigateUp = { },
        onClickItem = { }
    )
}