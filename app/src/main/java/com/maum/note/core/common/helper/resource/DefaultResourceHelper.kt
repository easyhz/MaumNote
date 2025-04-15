package com.maum.note.core.common.helper.resource

import android.content.Context
import com.maum.note.core.common.helper.resource.ResourceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultResourceHelper @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceHelper {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }
}
