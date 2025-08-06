package com.maum.note.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.maum.note.BuildConfig
import com.maum.note.R
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.core.designSystem.util.dialog.DialogAction
import com.maum.note.core.designSystem.util.dialog.DialogMessage
import com.maum.note.core.model.user.UserStep
import com.maum.note.domain.user.useacse.CheckUserStepUseCase
import com.maum.note.domain.user.useacse.ClearUserSessionUseCase
import com.maum.note.domain.user.useacse.SynchronizeUserUseCase
import com.maum.note.ui.screen.splash.contract.SplashSideEffect
import com.maum.note.ui.screen.splash.contract.SplashState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Date: 2025. 4. 18.
 * Time: 오후 11:32
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val checkUserStepUseCase: CheckUserStepUseCase,
    private val clearUserSessionUseCase: ClearUserSessionUseCase,
    private val synchronizeUserUseCase: SynchronizeUserUseCase,
    private val resourceHelper: ResourceHelper,
    private val logger: Logger,
) : BaseViewModel<SplashState, SplashSideEffect>(
    initialState = SplashState.init()
) {
    private val tag = "SplashViewModel"

    init {
        checkAppStep()
    }

    private fun checkAppStep() = viewModelScope.launch(ioDispatcher) {
        checkUserStepUseCase.invoke(Unit).onSuccess {
            withContext(mainDispatcher) {
                handleUserStep(it)
            }
        }.onFailure { e ->
            withContext(mainDispatcher) {
                signOutAndDelete()
                logger.e(tag, "checkAppStep: ${e.message}")
                handleError(e)
            }
        }
    }

    private suspend fun signOutAndDelete() {
        clearUserSessionUseCase.invoke(Unit).onFailure { e ->
            logger.e(tag, "signOutAndDelete: ${e.message}")
        }
    }

    private fun handleUserStep(userStep: UserStep) {
        when (userStep) {
            UserStep.NewUserToOnboarding, UserStep.ExistingUserToOnboarding -> navigateToOnboarding()
            UserStep.AlreadyLoginToMain -> navigateToHome()
            is UserStep.Maintenance -> handleMaintenance(userStep.notice)
            is UserStep.Update -> handleUpdate(userStep.newVersion)
            UserStep.NeedSynchronize -> handleSynchronize()
        }
    }

    private fun handleSynchronize() {
        viewModelScope.launch {
            synchronizeUserUseCase.invoke(10)
                .catch {
                    it.printStackTrace()
                }.onEach {
                    setState { copy(synchronizeState = it) }
                }.collect {
                    navigateToHome()
                }
        }
    }

    private fun navigateToOnboarding() {
        postSideEffect { SplashSideEffect.NavigateToOnboarding }
    }

    private fun navigateToHome() {
        postSideEffect { SplashSideEffect.NavigateToHome }
    }

    private fun navigateUp() {
        postSideEffect { SplashSideEffect.NavigateUp }
    }

    private fun setDialog(message: DialogMessage?) {
        setState { copy(dialogMessage = message) }
    }

    private fun handleUpdate(version: String) {
        setDialog(
            message = DialogMessage(
                title = resourceHelper.getString(R.string.version_dialog_title),
                message = resourceHelper.getString(
                    R.string.version_dialog_message,
                    version.toVersion(),
                    BuildConfig.VERSION_NAME.toVersion()
                ),
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.version_dialog_button),
                    onClick = ::updateAppVersion
                )
            )
        )
    }

    private fun handleMaintenance(message: String) {
        setDialog(
            message = DialogMessage(
                title = message,
                action = DialogAction.NavigateUp,
                positiveButton = getDefaultPositiveButton(
                    text = resourceHelper.getString(R.string.maintenance_dialog_button),
                    onClick = ::navigateUp
                )
            )
        )
    }

    private fun getDefaultPositiveButton(text: String, onClick: () -> Unit): BasicDialogButton {
        return BasicDialogButton(
            text = text,
            style = AppTypography.heading5_semiBold.copy(color = MainBackground),
            backgroundColor = Primary,
            onClick = onClick
        )
    }

    private fun handleError(e: Throwable) {
        when (e) {
            is AppError.DefaultError -> {
                navigateToOnboarding()
            }

            else -> {
                setDialog(
                    message = DialogMessage(
                        title = resourceHelper.getString(R.string.error_dialog_title),
                        message = resourceHelper.getString(R.string.error_dialog_message),
                        positiveButton = BasicDialogButton(
                            text = resourceHelper.getString(R.string.error_dialog_button),
                            style = AppTypography.heading5_semiBold.copy(color = MainBackground),
                            backgroundColor = Primary,
                            onClick = ::checkAppStep
                        )
                    )
                )
            }
        }
    }

    /* updateAppVersion */
    private fun updateAppVersion() {
        postSideEffect { SplashSideEffect.NavigateToUrl("https://play.google.com/store/apps/details?id=com.maum.note") }
    }

    private fun String.toVersion(): String {
        return "v$this"
    }
}