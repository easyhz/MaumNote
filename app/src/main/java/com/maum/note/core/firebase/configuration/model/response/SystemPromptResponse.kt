package com.maum.note.core.firebase.configuration.model.response

import com.google.firebase.firestore.PropertyName

data class SystemPromptResponse(
    @PropertyName("age0Desc")
    val age0Desc: String = "",
    @PropertyName("age1Desc")
    val age1Desc: String = "",
    @PropertyName("age2Desc")
    val age2Desc: String = "",
    @PropertyName("age3Desc")
    val age3Desc: String = "",
    @PropertyName("age4Desc")
    val age4Desc: String = "",
    @PropertyName("age5Desc")
    val age5Desc: String = "",
    @PropertyName("ageMixedDesc")
    val ageMixedDesc: String = "",
    @PropertyName("announcementContentPrompt")
    val announcementContentPrompt: String = "",
    @PropertyName("letterGreetingPrompt")
    val letterGreetingPrompt: String = "",
    @PropertyName("playContextPrompt")
    val playContextPrompt: String = "",
)