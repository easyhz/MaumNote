package com.maum.note.ui.screen.board.board

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.model.setting.BoardAdContent
import com.maum.note.domain.board.usecase.post.FetchPagesPostsUseCase
import com.maum.note.domain.configuration.usecase.FetchConfigurationUseCase
import com.maum.note.ui.screen.board.board.contract.BoardSideEffect
import com.maum.note.ui.screen.board.board.contract.BoardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
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
    private val fetchPagesPostsUseCase: FetchPagesPostsUseCase,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val logger: Logger,
) : BaseViewModel<BoardState, BoardSideEffect>(
    initialState = BoardState.init()
) {
    val postsFlow = fetchPagesPostsUseCase.invoke()
        .cachedIn(viewModelScope)
        .flowOn(ioDispatcher)

    init {
        fetchConfiguration()
    }

    fun onClickAd(boardAdContent: BoardAdContent) {
        postSideEffect { BoardSideEffect.NavigateToUrl(boardAdContent.directUrl) }
    }

    private fun fetchConfiguration() {
        viewModelScope.launch(ioDispatcher) {
            fetchConfigurationUseCase.invoke(Unit).onSuccess { configuration ->
                withContext(mainDispatcher) {
                    setState {
                        copy(configuration = configuration.toModel().let {
                            it.copy(boardAdContents = it.boardAdContents.shuffled())
                        })
                    }
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