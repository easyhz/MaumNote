package com.maum.note.ui.screen.setting.tone

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.common.di.dispatcher.AppDispatchers
import com.maum.note.core.common.di.dispatcher.Dispatcher
import com.maum.note.core.model.note.NoteType
import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.usecase.GetAllSelectedTonesUseCase
import com.maum.note.domain.tone.usecase.UpdateAllToneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingSideEffect
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Date: 2025. 4. 19.
 * Time: 오전 11:21
 */

@HiltViewModel
class ToneSettingViewModel @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @Dispatcher(AppDispatchers.MAIN) private val mainDispatcher: CoroutineDispatcher,
    private val getAllSelectedTonesUseCase: GetAllSelectedTonesUseCase,
    private val updateAllToneUseCase: UpdateAllToneUseCase,
) : BaseViewModel<ToneSettingState, ToneSettingSideEffect>(
    initialState = ToneSettingState.init()
) {

    init {
        init()
    }

    private fun init() {
        getAllSelectedTones()
    }

    fun onContentValueChange(
        noteType: NoteType,
        newValue: TextFieldValue
    ) {
        setState { copy(contents = contents.toMutableMap().apply { this[noteType] = newValue }) }
    }

    fun onClickSave() {
        updateAllTone()
    }

    private fun getAllSelectedTones() {
        viewModelScope.launch(ioDispatcher) {
            getAllSelectedTonesUseCase.invoke(Unit).onSuccess { tones ->
                val contents = tones.mapNotNull { tone ->
                    NoteType.getByValue(tone.noteType)?.let { type ->
                        type to TextFieldValue(tone.content)
                    }
                }.toMap()
                withContext(mainDispatcher) {
                    setState { copy(contents = contents) }
                }
            }.onFailure {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                }
            }
        }
    }

    private fun updateAllTone() {
        viewModelScope.launch(ioDispatcher) {
            val param = getUpdateToneRequestParam()
            updateAllToneUseCase.invoke(param).onSuccess {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                }
            }.onFailure {
                withContext(mainDispatcher) {
                    setState { copy(isLoading = false) }
                    // TODO SNACKBAR
                }
            }
        }
    }

    private fun getUpdateToneRequestParam(): List<UpdateToneRequestParam> {
        return currentState.contents.map { (noteType, textFieldValue) ->
            UpdateToneRequestParam(
                noteType = noteType.name,
                content = textFieldValue.text
            )
        }
    }
}