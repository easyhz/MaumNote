package com.maum.note.core.model.common

import com.maum.note.R
import com.maum.note.core.designSystem.component.bottomSheet.BottomSheetType

enum class OwnerBottomSheet: BottomSheetType {
    DELETE {
        override val titleId: Int
            get() = R.string.delete
        override val iconId: Int
            get() = R.drawable.ic_delete
    }
}


enum class ViewerBottomSheet: BottomSheetType {
    REPORT {
        override val titleId: Int
            get() = R.string.report
        override val iconId: Int
            get() = R.drawable.ic_report
    };
}