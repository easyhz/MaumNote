package com.maum.note.domain.setting.model.tone.response

data class ToneResponse(
    val id: String?,
    val userId: String,
    val common: String,
    val letterGreeting: String,
    val playContext: String,
    val announcementContent: String,
) {
    companion object {
        fun empty(userId: String): ToneResponse = ToneResponse(
            id = null,
            userId = userId,
            common = "",
            letterGreeting = "",
            playContext = "",
            announcementContent = ""
        )
    }
}
