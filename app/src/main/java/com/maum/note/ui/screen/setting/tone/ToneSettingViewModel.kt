package com.maum.note.ui.screen.setting.tone

import androidx.compose.ui.text.input.TextFieldValue
import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.model.note.NoteType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingSideEffect
import com.maum.note.ui.screen.setting.tone.contract.ToneSettingState

/**
 * Date: 2025. 4. 19.
 * Time: 오전 11:21
 */

@HiltViewModel
class ToneSettingViewModel @Inject constructor(

) : BaseViewModel<ToneSettingState, ToneSettingSideEffect>(
    initialState = ToneSettingState.init()
) {

    fun onContentValueChange(
        noteType: NoteType,
        newValue: TextFieldValue
    ) {
        setState { copy(contents = contents.toMutableMap().apply { this[noteType] = newValue }) }
    }

    fun onClickSave() {

    }
}