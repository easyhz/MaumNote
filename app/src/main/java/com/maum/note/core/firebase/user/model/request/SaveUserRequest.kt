package com.maum.note.core.firebase.user.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class SaveUserRequest(
    @PropertyName("id")
    val id: String = "",
    @PropertyName("creationTime")
    val creationTime: Timestamp = Timestamp.now(),
)
