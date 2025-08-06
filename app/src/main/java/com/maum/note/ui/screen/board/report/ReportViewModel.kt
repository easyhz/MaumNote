package com.maum.note.ui.screen.board.report

import com.maum.note.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.maum.note.ui.screen.board.report.contract.ReportSideEffect
import com.maum.note.ui.screen.board.report.contract.ReportState

/**
 * Date: 2025. 8. 6.
 * Time: 오후 12:25
 */

@HiltViewModel
class ReportViewModel @Inject constructor(

) : BaseViewModel<ReportState, ReportSideEffect>(
    initialState = ReportState.init()
) {

}