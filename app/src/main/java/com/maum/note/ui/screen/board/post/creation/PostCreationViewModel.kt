package com.maum.note.ui.screen.board.post.creation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.board.BoardReport
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import com.maum.note.domain.board.usecase.GetAnonymousSettingFlowUseCase
import com.maum.note.domain.board.usecase.SetAnonymousSettingUseCase
import com.maum.note.domain.board.usecase.post.CreatePostUseCase
import com.maum.note.ui.screen.board.post.creation.contract.PostCreationSideEffect
import com.maum.note.ui.screen.board.post.creation.contract.PostCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 7. 25.
 * Time: 오후 6:15
 */

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val createPostUseCase: CreatePostUseCase,
    private val getAnonymousSettingFlowUseCase: GetAnonymousSettingFlowUseCase,
    private val setAnonymousSettingUseCase: SetAnonymousSettingUseCase,
    private val resourceHelper: ResourceHelper,
) : BaseViewModel<PostCreationState, PostCreationSideEffect>(
    initialState = PostCreationState.init()
) {

    init {
        getAnonymousSetting()
    }

    private fun getAnonymousSetting() {
        viewModelScope.launch {
            getAnonymousSettingFlowUseCase.invoke().flowOn(ioDispatcher).catch { e ->
                e.printStackTrace()
            }.collect {
                setState { copy(isAnonymous = it) }
            }
        }
    }

    fun onClickNext() {
        viewModelScope.launch(ioDispatcher) {
            if(!validData()) return@launch
            setLoading(true)
            val param = CreatePostRequest(
                title = currentState.titleText.text,
                content = currentState.contentText.text,
                isAnonymous = currentState.isAnonymous
            )
            createPostUseCase.invoke(param).onSuccess {
                withContext(mainDispatcher) {
                    navigateToBoard()
                }
            }.onFailure {
                it.printStackTrace()
                setErrorDialog(it)
            }.also {
                setLoading(false)
            }
        }
    }

    fun onValueChangeTitle(value: TextFieldValue) {
        setState { copy(titleText = value, enabledButton = value.text.isNotBlank()) }
    }

    fun onValueChangeContent(value: TextFieldValue) {
        setState { copy(contentText = value) }
    }

    fun setIsAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
        setAnonymousSetting()
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

    private fun navigateToBoard() {
        postSideEffect { PostCreationSideEffect.NavigateToBoard }
    }


    private fun setAnonymousSetting() {
        viewModelScope.launch(ioDispatcher) {
            val isAnonymous = currentState.isAnonymous
            setAnonymousSettingUseCase.invoke(isAnonymous)
        }
    }

    private fun setErrorDialog(error: Throwable) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.error_dialog_title),
                message = resourceHelper.getString(error.handleError()),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::hideDialog
                )
            )
        )
    }

    private fun validData(): Boolean {
        if(currentState.titleText.text.isNotBlank()) return true
        setNoDataErrorDialog()
        return false
    }

    private fun setNoDataErrorDialog() {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.board_creation_post_title_dialog),
                message = resourceHelper.getString(R.string.board_creation_post_title_dialog_content),
                positiveButton = BasicDialogButton.default(
                    text = resourceHelper.getString(R.string.dialog_positive_button),
                    onClick = ::hideDialog
                )
            )
        )
    }


    private fun setDialog(message: DialogMessage?) {
        setState { copy(dialogMessage = message) }
    }

    private fun hideDialog() {
        setDialog(null)
    }
}