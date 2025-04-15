package com.maum.note.core.common.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.helper.resource.ResourceHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MVVM 을 위한 [BaseViewModel]
 *
 * [UiState] State : 상태
 * [SideEffect] SideEffect: 부수 효과
 */
abstract class BaseViewModel<State: UiState, SideEffect: UiSideEffect>(
    initialState: State
): ViewModel() {
    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State>
        get() = _uiState.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()


    /**
     * [State] 설정
     */
    fun reduce(reducer: State.() -> State) { _uiState.value = currentState.reducer() }

    /**
     * [SideEffect] 설정
     */
    fun postSideEffect(builder: () -> SideEffect) = viewModelScope.launch { _sideEffect.emit(builder()) }

    fun showSnackBar(
        resourceHelper: ResourceHelper,
        @StringRes value: Int,
        vararg formatArgs: Any,
        sideEffect: (String) -> SideEffect
    ) {
        val snackBarString = resourceHelper.getString(value, *formatArgs)
        postSideEffect { sideEffect(snackBarString) }
    }
}