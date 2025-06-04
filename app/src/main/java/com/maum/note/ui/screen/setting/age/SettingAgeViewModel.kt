package com.maum.note.ui.screen.setting.age

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.SettingAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.model.note.AgeType
import com.maum.note.domain.setting.usecase.age.GetAgeSettingUseCase
import com.maum.note.domain.setting.usecase.age.UpdateAgeSettingUseCase
import com.maum.note.domain.user.useacse.UpdateUserStudentAgeUseCase
import com.maum.note.ui.screen.setting.age.contract.SettingAgeSideEffect
import com.maum.note.ui.screen.setting.age.contract.SettingAgeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 4. 18.
 * Time: 오후 6:00
 */

@HiltViewModel
class SettingAgeViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val resourceHelper: ResourceHelper,
    private val getAgeSettingUseCase: GetAgeSettingUseCase,
    private val updateAgeSettingUseCase: UpdateAgeSettingUseCase,
    private val updateUserStudentAgeUseCase: UpdateUserStudentAgeUseCase,
) : BaseViewModel<SettingAgeState, SettingAgeSideEffect>(
    initialState = SettingAgeState.init()
) {

    init {
        init()
    }

    private fun init() {
        getAgeSetting()
    }

    fun onClickNext(ageType: AgeType) {
        updateAge(ageType)
    }

    private fun getAgeSetting() {
        viewModelScope.launch(ioDispatcher) {
            getAgeSettingUseCase.invoke(Unit)
                .onSuccess {
                    withContext(mainDispatcher) {
                        val age = AgeType.getByValue(it) ?: AgeType.MIXED
                        setState { copy(age = age) }
                        postSideEffect { SettingAgeSideEffect.SetPickerAge(ageType = age) }
                    }
                }
        }
    }


    private fun updateAge(ageType: AgeType) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                updateUserStudentAgeUseCase.invoke(ageType.name).getOrThrow()
                updateAgeSettingUseCase.invoke(ageType.name).getOrThrow()
            }.onSuccess {
                logEvent()
                navigateToNext()
            }.onFailure { e ->
                e.printStackTrace()
                showSnackBar(
                    resourceHelper = resourceHelper,
                    value = e.handleError()
                ) { SettingAgeSideEffect.ShowSnackBar(it) }
            }
        }
    }

    private fun navigateToNext() {
        postSideEffect { SettingAgeSideEffect.NavigateToNext }
    }

    private fun logEvent() {
        AnalyticsManager.logEvent(SettingAnalyticsEvent.SETTING_STUDENT_AGE_CHANGED)
    }
}