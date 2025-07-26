package com.maum.note.ui.screen.board.board

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.model.setting.BoardAdContent
import com.maum.note.domain.board.usecase.FetchPostsUseCase
import com.maum.note.domain.configuration.usecase.FetchConfigurationUseCase
import com.maum.note.ui.screen.board.board.contract.BoardSideEffect
import com.maum.note.ui.screen.board.board.contract.BoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 7. 25.
 * Time: 오후 9:09
 */

@HiltViewModel
class BoardViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val fetchPostsUseCase: FetchPostsUseCase,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val logger: Logger,
) : BaseViewModel<BoardState, BoardSideEffect>(
    initialState = BoardState.init()
) {
    init {
        fetchPosts()
        fetchConfiguration()
    }

    fun onClickAd(boardAdContent: BoardAdContent) {
        postSideEffect { BoardSideEffect.NavigateToUrl(boardAdContent.directUrl) }
    }

    private fun fetchPosts() {
        viewModelScope.launch(ioDispatcher) {
            setLoading(true)
            fetchPostsUseCase.invoke(Unit).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(postList = it) } // TODO 페이징 처리
                }
            }.onFailure {
                // TODO 에러 처리
            }.also {
                setLoading(false)
            }
        }
    }

    private fun fetchConfiguration() {
        viewModelScope.launch(ioDispatcher) {
            fetchConfigurationUseCase.invoke(Unit).onSuccess { configuration ->
                withContext(mainDispatcher) {
                    setState { copy(configuration = configuration.toModel()) }
                }
            }.onFailure { error ->
                logger.e("SettingViewModel", "Error fetching configuration: $error")
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        setState { copy(isLoading = isLoading) }
    }

}