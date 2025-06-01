package com.maum.note.ui.screen.onboarding.age

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.analytics.event.OnboardingAnalyticsEvent
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.model.note.AgeType
import com.maum.note.domain.setting.usecase.age.GetAgeSettingUseCase
import com.maum.note.domain.setting.usecase.age.UpdateAgeSettingUseCase
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeSideEffect
import com.maum.note.ui.screen.onboarding.age.contract.OnboardingAgeState
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
class OnboardingAgeViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val getAgeSettingUseCase: GetAgeSettingUseCase,
    private val updateAgeSettingUseCase: UpdateAgeSettingUseCase,
) : BaseViewModel<OnboardingAgeState, OnboardingAgeSideEffect>(
    initialState = OnboardingAgeState.init()
) {

    init {
        init()
    }

    private fun init() {
        getAgeSetting()
    }

    fun onClickNext(ageType: AgeType) {
        updateAgeSetting(ageType)
    }

    private fun getAgeSetting() {
        viewModelScope.launch(ioDispatcher) {
            getAgeSettingUseCase.invoke(Unit)
                .onSuccess {
                    withContext(mainDispatcher) {
                        val age = AgeType.getByValue(it) ?: AgeType.MIXED
                        setState { copy(age = age) }
                        postSideEffect { OnboardingAgeSideEffect.SetPickerAge(ageType = age) }
                    }
                }
        }
    }

    private fun updateAgeSetting(ageType: AgeType) {
        viewModelScope.launch(ioDispatcher) {
            updateAgeSettingUseCase.invoke(ageType.name).onSuccess {
                logEvent(ageType)
                navigateToNext()
            }
        }
    }

    private fun navigateToNext() {
        postSideEffect { OnboardingAgeSideEffect.NavigateToNext }
    }

    private fun logEvent(ageType: AgeType) {
        val event = ageType.getOnboardingLogEvent()
        AnalyticsManager.logEvent(event)
    }
}