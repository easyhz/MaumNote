package com.maum.note.core.navigation.setting.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
object Setting: Parcelable {
    @Serializable
    data object Main

    @Serializable
    data object Tone

    @Serializable
    data object Age

    @Serializable
    data object Profile
}