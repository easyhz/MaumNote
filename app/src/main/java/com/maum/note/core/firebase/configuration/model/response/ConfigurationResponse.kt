package com.maum.note.core.firebase.configuration.model.response

import com.google.firebase.firestore.PropertyName

data class ConfigurationResponse(
    @PropertyName("androidVersion")
    val androidVersion: String = "",
    @PropertyName("iosVersion")
    val iosVersion: String = "",
    @get:PropertyName("notionURL")
    @set:PropertyName("notionURL")
    var notionUrl: String = "",
    @PropertyName("maintenanceNotice")
    val maintenanceNotice: String = "",
)
