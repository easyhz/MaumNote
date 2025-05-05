package com.maum.note.core.common.error

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val resourceHelper: ResourceHelper,
) {
    fun getErrorMessage(throwable: Throwable?): String? {
        if (throwable == null) return null
        val resId = when (throwable) {
            is AppError.UnexpectedError -> R.string.unexpected_error
            is AppError.NoResultError -> R.string.no_result_error
            is AppError.NetworkConnectionError -> R.string.network_connection_error
            else -> null
        }

        return resId?.let(resourceHelper::getString)
            ?: throwable.message
            ?: resourceHelper.getString(R.string.unexpected_error)
    }
}