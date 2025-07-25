package com.maum.note.ui.screen.board.board

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.domain.board.usecase.FetchPostsUseCase
import com.maum.note.ui.screen.board.board.contract.BoardSideEffect
import com.maum.note.ui.screen.board.board.contract.BoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase,
) : BaseViewModel<BoardState, BoardSideEffect>(
    initialState = BoardState.init()
) {
    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            setLoading(true)
            fetchPostsUseCase.invoke(Unit).onSuccess {
                setState { copy(postList = it) }
            }.onFailure {
                // TODO 에러 처리
            }.also {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

}