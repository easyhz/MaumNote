package com.maum.note.ui.screen.setting.profile

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.domain.user.useacse.FetchUserUseCase
import com.maum.note.domain.user.useacse.IsNicknameDuplicatedUseCase
import com.maum.note.domain.user.useacse.UpdateUserNicknameUseCase
import com.maum.note.ui.screen.setting.profile.contract.CaptionType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.profile.contract.ProfileSideEffect
import com.maum.note.ui.screen.setting.profile.contract.NicknameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date: 2025. 8. 3.
 * Time: 오후 10:26
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val fetchUserUseCase: FetchUserUseCase,
    private val isNicknameDuplicatedUseCase: IsNicknameDuplicatedUseCase,
    private val updateUserNicknameUseCase: UpdateUserNicknameUseCase
) : BaseViewModel<NicknameState, ProfileSideEffect>(
    initialState = NicknameState.init()
) {
    private val tag = this::class.simpleName
    private var checkNicknameJob: Job? = null

    init {
        fetchUserNickname()
    }

    private fun fetchUserNickname() {
        viewModelScope.launch {
            fetchUserUseCase.invoke(Unit).onSuccess {
                setState {
                    copy(
                        nicknameText = TextFieldValue(it.nickname ?: ""),
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                Log.e(tag, "fetchUserNickname: ${e.message}", e)
                setState { copy(isLoading = false) }
            }
        }
    }

    fun onImeVisibilityChanged(isVisible: Boolean) {
        setState { copy(isImeVisible = isVisible) }
    }

    fun onValueChangeNickname(value: TextFieldValue) {
        setState { copy(nicknameText = value, buttonLoading = true) }

        checkNicknameJob?.cancel()
        checkNicknameJob = viewModelScope.launch {
            delay(800)
            checkNickname()
        }
    }

    fun onClickSave() {
        viewModelScope.launch {
            val nickname = currentState.nicknameText.text
            setState { copy(isLoading = true) }
            if (validateNickname(nickname) != CaptionType.DEFAULT) return@launch
            updateUserNicknameUseCase.invoke(nickname).onSuccess {
                setState { copy(isLoading = false) }
                navigateUp()
            }.onFailure { e ->
                setState { copy(isLoading = false) }
                postSideEffect { ProfileSideEffect.ShowToast(resourceHelper.getString(e.handleError())) }
            }
        }
    }

    private suspend fun checkNickname() {
        val nickname = currentState.nicknameText.text
        try {
            val captionType = validateNickname(nickname)
            val enabledButton = captionType == CaptionType.DEFAULT && nickname.isNotBlank()
            setState {
                copy(
                    captionType = captionType,
                    enabledButton = enabledButton,
                    buttonLoading = false
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()

            val message = e.handleError()

            setState { copy(enabledButton = false, buttonLoading = false) }
            postSideEffect { ProfileSideEffect.ShowToast(resourceHelper.getString(message)) }
        }
    }

    private suspend fun validateNickname(nickname: String): CaptionType {
        val isDuplicated = isNicknameDuplicatedUseCase.invoke(nickname).getOrThrow()

        return when {
            isDuplicated -> CaptionType.DUPLICATED
            nickname.contains(" ") -> CaptionType.BLANK
            nickname.length < 3 -> CaptionType.LENGTH
            else -> CaptionType.DEFAULT
        }
    }

    private fun navigateUp() {
        postSideEffect { ProfileSideEffect.NavigateUp }
    }
}