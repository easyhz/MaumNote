package com.maum.note.core.firebase.configuration.model.response

import com.google.firebase.firestore.PropertyName

data class BoardAdContentsResponse(
    @get:PropertyName("directURL")
    @set:PropertyName("directURL")
    var directUrl: String = "",
    @get:PropertyName("imageURL")
    @set:PropertyName("imageURL")
    var imageUrl: String = "",
)