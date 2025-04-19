package com.maum.note.ui.screen.setting.setting

import com.maum.note.core.common.base.BaseViewModel
import com.maum.note.core.model.setting.EtcSettingItem
import com.maum.note.core.model.setting.NoteSettingItem
import com.maum.note.core.model.setting.SettingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.setting.setting.contract.SettingSideEffect
import com.maum.note.ui.screen.setting.setting.contract.SettingState

/**
 * Date: 2025. 4. 18.
 * Time: 오후 4:44
 */

@HiltViewModel
class SettingViewModel @Inject constructor(

) : BaseViewModel<SettingState, SettingSideEffect>(
    initialState = SettingState.init()
) {

    fun onClickItem(item: SettingItem) {
        when (item) {
            is NoteSettingItem -> handleNoteSettingItem(item)
            is EtcSettingItem -> handleEtcSettingItem(item)
        }
    }

    private fun handleNoteSettingItem(item: NoteSettingItem) {
        when (item) {
            NoteSettingItem.AGE -> {
                // navigate to age setting screen
            }
            NoteSettingItem.TONE -> {
                // navigate to tone setting screen
            }
            NoteSettingItem.TRASH -> {
                // navigate to trash screen
            }
        }
    }

    private fun handleEtcSettingItem(item: EtcSettingItem) {
        when (item) {
            EtcSettingItem.ABOUT -> {
                // navigate to about screen
            }
            EtcSettingItem.VERSION -> { }
        }
    }
}