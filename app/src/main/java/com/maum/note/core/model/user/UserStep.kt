package com.maum.note.core.model.user

sealed class UserStep {
    data object NewUserToOnboarding : UserStep()
    data object ExistingUserToOnboarding : UserStep()
    data object AlreadyLoginToMain : UserStep()
    data class Maintenance(val notice: String) : UserStep()
    data class Update(val newVersion: String) : UserStep()
}