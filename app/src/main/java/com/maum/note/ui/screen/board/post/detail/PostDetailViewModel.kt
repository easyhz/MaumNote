package com.maum.note.ui.screen.board.post.detail

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.domain.board.usecase.comment.FetchCommentsUseCase
import com.maum.note.domain.board.usecase.post.FetchPostUseCase
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailSideEffect
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
) : BaseViewModel<PostDetailState, PostDetailSideEffect>(
    initialState = PostDetailState.init()
) {
    
    init {
        init()
    }

    private fun init() {
        val id: String? = savedStateHandle["id"]
        val title: String = savedStateHandle["title"] ?: ""

        if (id == null) return navigateUp()

        setState { copy(post = post?.copy(id = id, title = title)) }
        fetchPost(id = id)
        fetchComments(id = id)
    }

    fun onCommentTextChanged(value: TextFieldValue) {
        setState { copy(commentText = value) }
    }

    fun onClickAnonymous() {
        setState { copy(isAnonymous = !isAnonymous) }
    }

    fun onClickSend() {

    }

    private fun fetchPost(id: String = currentState.post?.id ?: "") {
        if (id.isEmpty()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchPostUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(post = it) }
                }
            }.onFailure {
                // ERROR 처리
            }
            setLoading(false)
        }
    }

    private fun fetchComments(id: String = currentState.post?.id ?: "") {
        if (id.isEmpty()) return

        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchCommentsUseCase.invoke(param = id).onSuccess {
                withContext(mainDispatcher) {
                    println("comments: $it")
                    setState { copy(comments = it) }
                }
            }.onFailure {
                // ERROR 처리
                it.printStackTrace()
            }
            setLoading(false)
        }
    }

    private fun navigateUp() {
        
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

}