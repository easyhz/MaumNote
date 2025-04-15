package com.maum.note.ui.screen.home

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.home.contract.HomeSideEffect
import com.maum.note.ui.screen.home.contract.HomeState

/**
 * Date: 2025. 4. 15.
 * Time: 오후 7:45
 */

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel<HomeState, HomeSideEffect>(
    initialState = HomeState.init()
) {

}