package com.maum.note.ui.screen.onboarding.age

import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.analytics.AnalyticsManager
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.handleError
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.model.note.AgeType
import com.maum.note.domain.setting.usecase.age.GetAgeTypeUseCase
import com.maum.note.domain.setting.usecase.age.UpdateAgeSettingUseCase
import com.maum.note.domain.user.useacse.UpdateUserStudentAgeUseCase
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
    private val resourceHelper: ResourceHelper,
    private val getAgeTypeUseCase: GetAgeTypeUseCase,
    private val updateAgeSettingUseCase: UpdateAgeSettingUseCase,
    private val updateUserStudentAgeUseCase: UpdateUserStudentAgeUseCase,
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
        updateAge(ageType)
    }

    private fun getAgeSetting() {
        viewModelScope.launch(ioDispatcher) {
            getAgeTypeUseCase.invoke(Unit)
                .onSuccess { age ->
                    withContext(mainDispatcher) {
                        setState { copy(age = age) }
                        postSideEffect { OnboardingAgeSideEffect.SetPickerAge(ageType = age) }
                    }
                }
        }
    }

    private fun updateAge(ageType: AgeType) {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                updateUserStudentAgeUseCase.invoke(ageType.alias).getOrThrow()
                updateAgeSettingUseCase.invoke(ageType.name).getOrThrow()
            }.onSuccess {
                logEvent(ageType)
                navigateToNext()
            }.onFailure { e ->
                e.printStackTrace()
                showSnackBar(
                    resourceHelper = resourceHelper,
                    value = e.handleError()
                ) { OnboardingAgeSideEffect.ShowSnackBar(it) }
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