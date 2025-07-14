package com.maum.note.ui.screen.setting.setting

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.model.setting.Configuration
import com.maum.note.core.model.setting.EtcSettingItem
import com.maum.note.core.model.setting.NoteSettingItem
import com.maum.note.core.model.setting.SettingItem
import com.maum.note.domain.configuration.model.response.ConfigurationResponse
import com.maum.note.domain.configuration.usecase.FetchConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.setting.contract.SettingSideEffect
import com.maum.note.ui.screen.setting.setting.contract.SettingState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val fetchConfigurationUseCase: FetchConfigurationUseCase,
    private val logger: Logger,
) : BaseViewModel<SettingState, SettingSideEffect>(
    initialState = SettingState.init()
) {
    init {
        fetchConfiguration()
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

    fun onClickItem(item: SettingItem) {
        when (item) {
            is NoteSettingItem -> handleNoteSettingItem(item)
            is EtcSettingItem -> handleEtcSettingItem(item)
        }
    }

    private fun handleNoteSettingItem(item: NoteSettingItem) {
        when (item) {
            NoteSettingItem.AGE -> {
                navigateToAgeSetting()
            }
            NoteSettingItem.TONE -> {
                navigateToToneSetting()
            }
            NoteSettingItem.LEGACY_NOTE -> {

            }
        }
    }

    private fun handleEtcSettingItem(item: EtcSettingItem) {
        when (item) {
            EtcSettingItem.ABOUT -> {
                navigateToUrl(currentState.configuration.notionUrl)
            }
            EtcSettingItem.VERSION -> { }
        }
    }

    private fun navigateToToneSetting() {
        postSideEffect { SettingSideEffect.NavigateToToneSetting }
    }

    private fun navigateToAgeSetting() {
        postSideEffect { SettingSideEffect.NavigateToAgeSetting }
    }

    private fun navigateToUrl(url: String){
        postSideEffect { SettingSideEffect.NavigateToUrl(url) }
    }


    private fun ConfigurationResponse.toModel() = Configuration(
        androidVersion = androidVersion,
        notionUrl = notionUrl,
        maintenanceNotice = maintenanceNotice,
    )
}