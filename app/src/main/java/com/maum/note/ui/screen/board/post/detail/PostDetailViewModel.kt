package com.maum.note.ui.screen.board.post.detail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.usecase.GetAnonymousSettingFlowUseCase
import com.maum.note.domain.board.usecase.SetAnonymousSettingUseCase
import com.maum.note.domain.board.usecase.comment.CreateCommentUseCase
import com.maum.note.domain.board.usecase.comment.FetchCommentsUseCase
import com.maum.note.domain.board.usecase.post.FetchPostUseCase
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailSideEffect
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val fetchPostUseCase: FetchPostUseCase,
    private val fetchCommentsUseCase: FetchCommentsUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val getAnonymousSettingFlowUseCase: GetAnonymousSettingFlowUseCase,
    private val setAnonymousSettingUseCase: SetAnonymousSettingUseCase,
) : BaseViewModel<PostDetailState, PostDetailSideEffect>(
    initialState = PostDetailState.init()
) {

    init {
        init()
        getAnonymousSetting()
    }

    private fun init() {
        val id: String? = savedStateHandle["id"]
        val title: String = savedStateHandle["title"] ?: ""

        if (id == null) return navigateUp()

        setState { copy(post = post?.copy(id = id, title = title)) }
        fetchPost(id = id)
        fetchComments(id = id)
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

    fun onCommentTextChanged(value: TextFieldValue) {
        setState { copy(commentText = value) }
    }

    fun onClickAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
        setAnonymousSetting()
    }

    fun onClickSend() {
        if (currentState.commentText.text.isBlank()) return
        val postId = currentState.post?.id
        if (postId.isNullOrBlank()) return
        val param = CreateCommentRequest(
            postId = postId,
            content = currentState.commentText.text,
            isAnonymous = currentState.isAnonymous
        )
        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            createCommentUseCase.invoke(param).onSuccess {
                setState { copy(commentText = TextFieldValue("")) }
            }.onFailure {
                // ERROR 처리
            }.also {
                setLoading(false)
            }
            fetchComments(id = postId)
        }

    }

    private fun fetchPost(id: String = currentState.post?.id ?: "") {
        if (id.isBlank()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchPostUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(post = it) }
                }
            }.onFailure {
                // ERROR 처리
            }.also {
                setLoading(false)
            }
        }
    }

    private fun fetchComments(id: String = currentState.post?.id ?: "") {
        if (id.isBlank()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchCommentsUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(comments = it) }
                }
            }.onFailure {
                // ERROR 처리
                it.printStackTrace()
            }.also {
                setLoading(false)
            }
        }
    }

    private fun navigateUp() {
        postSideEffect { PostDetailSideEffect.NavigateUp }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

    private fun setAnonymousSetting() {
        viewModelScope.launch(ioDispatcher) {
            val isAnonymous = currentState.isAnonymous
            setAnonymousSettingUseCase.invoke(isAnonymous)
        }
    }

}