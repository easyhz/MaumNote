package com.maum.note.ui.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.maum.note.R
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.HomeAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.model.note.Note
import com.maum.note.domain.configuration.usecase.ShouldNotificationPermissionUseCase
import com.maum.note.domain.configuration.usecase.UpdateNotificationPermissionUseCase
import com.maum.note.domain.note.usecase.GetPagedNotesUseCase
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val getPagedNotesUseCase: GetPagedNotesUseCase,
    private val resourceHelper: ResourceHelper,
    private val shouldNotificationPermissionUseCase: ShouldNotificationPermissionUseCase,
    private val updateNotificationPermissionUseCase: UpdateNotificationPermissionUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(
    initialState = HomeState.init()
) {
    val notesFlow = getPagedNotesUseCase()
        .cachedIn(viewModelScope)
        .flowOn(ioDispatcher)

    init {
        init()
//        findAllNotes()
    }

    private fun init() {
        checkNotificationPermission()
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
}