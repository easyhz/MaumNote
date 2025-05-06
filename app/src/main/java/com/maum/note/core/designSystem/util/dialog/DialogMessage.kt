package com.maum.note.core.designSystem.util.dialog

data class DialogMessage(
    val title: String,
    val message: String? = null,
    val action: DialogAction? = null,
    val positiveButton: BasicDialogButton? = null,
    val negativeButton: BasicDialogButton? = null,
)

sealed class DialogAction{
    data object Retry: DialogAction()
    data object Dismiss: DialogAction()
    data object NavigateUp: DialogAction()
    data object Clear: DialogAction()
    data object CustomAction: DialogAction()
    data class WithParam<T>(val param: T): DialogAction()
}
