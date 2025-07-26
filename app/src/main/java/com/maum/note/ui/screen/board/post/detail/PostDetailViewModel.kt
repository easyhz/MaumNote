package com.maum.note.ui.screen.board.post.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.domain.board.usecase.FetchPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailSideEffect
import com.maum.note.ui.screen.board.post.detail.contract.PostDetailState
import kotlinx.coroutines.launch

/**
 * Date: 2025. 7. 26.
 * Time: 오후 5:38
 */

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchPostUseCase: FetchPostUseCase
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
    }

    private fun fetchPost(id: String = currentState.post?.id ?: "") {
        if (id.isEmpty()) return

        viewModelScope.launch {
            setLoading(true)
            fetchPostUseCase.invoke(param = id).onSuccess {
                setState { copy(post = it) }
            }.onFailure {
                // ERROR 처리
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