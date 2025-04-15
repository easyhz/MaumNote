package com.maum.note.core.common.helper.resource

import androidx.annotation.StringRes

interface ResourceHelper {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String
}