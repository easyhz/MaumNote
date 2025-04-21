package com.maum.note.core.navigation.onboarding.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
internal object Onboarding: Parcelable {
    @Serializable
    data object Start

    @Serializable
    data object Age

    @Serializable
    data object Tone
}