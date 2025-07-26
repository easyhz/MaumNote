package com.maum.note.ui.screen.board.post.creation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import com.maum.note.domain.board.usecase.post.CreatePostUseCase
import com.maum.note.ui.screen.board.post.creation.contract.PostCreationSideEffect
import com.maum.note.ui.screen.board.post.creation.contract.PostCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 7. 25.
 * Time: 오후 6:15
 */

@HiltViewModel
class PostCreationViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase,
) : BaseViewModel<PostCreationState, PostCreationSideEffect>(
    initialState = PostCreationState.init()
) {

    fun onClickNext() {
        viewModelScope.launch {
            setLoading(true)
            val param = CreatePostRequest(
                title = currentState.titleText.text,
                content = currentState.contentText.text,
                isAnonymous = currentState.isAnonymous
            )
            createPostUseCase.invoke(param).onSuccess {
                println("성공")
            }.onFailure {
                println("Error occurred: ${it.message}")
            }.also {
                setLoading(false)
            }
        }
    }

    fun onValueChangeTitle(value: TextFieldValue) {
        setState { copy(titleText = value) }
    }

    fun onValueChangeContent(value: TextFieldValue) {
        setState { copy(contentText = value) }
    }

    fun setIsAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }
}