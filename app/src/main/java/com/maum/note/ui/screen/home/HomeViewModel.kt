package com.maum.note.ui.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maum.note.R
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.HomeAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.model.note.Note
import com.maum.note.core.model.setting.AdContent
import com.maum.note.domain.configuration.usecase.FetchConfigurationUseCase
import com.maum.note.domain.configuration.usecase.ShouldNotificationPermissionUseCase
import com.maum.note.domain.configuration.usecase.UpdateNotificationPermissionUseCase
import com.maum.note.domain.note.usecase.FetchPagedNotesUseCase
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val logger: Logger,
    private val fetchPagedNotesUseCase: FetchPagedNotesUseCase,
    private val resourceHelper: ResourceHelper,
    private val shouldNotificationPermissionUseCase: ShouldNotificationPermissionUseCase,
    private val updateNotificationPermissionUseCase: UpdateNotificationPermissionUseCase,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(
    initialState = HomeState.init()
) {
    val notesFlow = fetchPagedNotesUseCase.invoke()
        .cachedIn(viewModelScope)
        .flowOn(ioDispatcher)

    init {
        init()
    }

    private fun init() {
        checkNotificationPermission()
        fetchConfiguration()
    }

    private fun checkNotificationPermission() {
        viewModelScope.launch(ioDispatcher) {
            shouldNotificationPermissionUseCase.invoke(Unit).onSuccess {
                setState { copy(needNotificationPermission = it) }
            }.onFailure {
                setState { copy(needNotificationPermission = true) }
            }
        }
    }


    private fun fetchConfiguration() {
        viewModelScope.launch(ioDispatcher) {
            fetchConfigurationUseCase.invoke(Unit).onSuccess { configuration ->
                withContext(mainDispatcher) {
                    setState {
                        copy(configuration = configuration.toModel().let {
                            it.copy(adContents = it.adContents.shuffled())
                        })
                    }
                }
            }.onFailure { error ->
                logger.e("HomeViewModel", "Error fetching configuration: $error")
            }
        }
    }

    fun onClickCopyButton(note: Note) {
        postSideEffect { HomeSideEffect.CopyToClipboard(note.result) }
        logEventCopyButtonClick()
        showSnackBar(
            resourceHelper = resourceHelper,
            value = R.string.note_copy_success
        ) { HomeSideEffect.ShowSnackBar(it) }
    }


    fun updatePushNotificationStatus(isPushEnabled: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            updateNotificationPermissionUseCase.invoke(isPushEnabled).also {
                setState { copy(needNotificationPermission = false) }
            }
        }
    }

    private fun logEventCopyButtonClick() {
        AnalyticsManager.logEvent(HomeAnalyticsEvent.NOTE_COPIED)
    }

    fun logEventNoteSelected() {
        AnalyticsManager.logEvent(HomeAnalyticsEvent.NOTE_SELECTED)
    }

    fun onClickAd(adContent: AdContent) {
        postSideEffect { HomeSideEffect.NavigateToUrl(adContent.directUrl) }
    }
}