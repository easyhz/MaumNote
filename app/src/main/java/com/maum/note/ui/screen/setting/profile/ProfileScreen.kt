package com.maum.note.ui.screen.setting.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maum.note.R
import com.maum.note.core.common.util.collect.collectInSideEffectWithLifecycle
import com.maum.note.core.designSystem.component.button.MainButton
import com.maum.note.core.designSystem.component.loading.FullLoadingIndicator
import com.maum.note.core.designSystem.component.scaffold.AppScaffold
import com.maum.note.core.designSystem.component.textField.ContentTextFieldWithTitle
import com.maum.note.core.designSystem.component.topbar.TopBar
import com.maum.note.core.designSystem.component.topbar.TopBarIcon
import com.maum.note.core.designSystem.component.topbar.TopBarText
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.designSystem.util.textField.TextFieldType
import com.maum.note.ui.screen.setting.profile.contract.NicknameState
import com.maum.note.ui.screen.setting.profile.contract.ProfileSideEffect
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.LocalSnackBarHostState

/**
 * Date: 2025. 8. 3.
 * Time: 오후 10:26
 */

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val snackBarHost = LocalSnackBarHostState.current

    LaunchedEffect(imeVisible) {
        viewModel.onImeVisibilityChanged(imeVisible)
    }
    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        clearFocus = focusManager::clearFocus,
        navigateUp = navigateUp,
        onClickSave = viewModel::onClickSave,
        onValueChangeNickname = viewModel::onValueChangeNickname
    )

    viewModel.sideEffect.collectInSideEffectWithLifecycle { sideEffect ->
        when(sideEffect){
            is ProfileSideEffect.ShowSnackBar -> {
                snackBarHost.showSnackbar(message = sideEffect.message)
            }
            is ProfileSideEffect.ShowToast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_LONG).show()
            }
            is ProfileSideEffect.NavigateUp -> navigateUp()
        }
    }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: NicknameState,
    clearFocus: () -> Unit = {},
    navigateUp: () -> Unit = {},
    onClickSave: () -> Unit = {},
    onValueChangeNickname: (TextFieldValue) -> Unit = {},
) {
    AppScaffold(
        modifier = modifier.noRippleClickable { clearFocus() },
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
                        text = stringResource(id = R.string.setting_board_profile_title),
                        alignment = Alignment.Center,
                    )
                }
            )
        },
        bottomBar = {
            MainButton(
                text = stringResource(R.string.onboarding_start_button),
                enabled = uiState.enabledButton,
                isImeVisible = uiState.isImeVisible,
                isLoading = uiState.buttonLoading,

                onClick = onClickSave,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ContentTextFieldWithTitle(
                title = stringResource(R.string.setting_board_profile_title),
                value = uiState.nicknameText,
                onValueChange = onValueChangeNickname,
                textFieldType = TextFieldType.Single(),
                singleLine = true,
                placeholder = stringResource(R.string.board_creation_post_title_placeholder),
            )

            AnimatedContent(
                targetState = uiState.captionType,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                }
            ) { type ->
                Text(
                    text = stringResource(type.captionText),
                    style = AppTypography.body1_regular.copy(
                        color = type.color
                    ),
                )
            }
        }
    }

    FullLoadingIndicator(
        isLoading = uiState.isLoading
    )
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = NicknameState.init()
    )
}