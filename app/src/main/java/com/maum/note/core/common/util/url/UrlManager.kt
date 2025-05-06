package com.maum.note.core.common.util.url

import android.net.Uri


fun String.urlEncode(): String {
    if(this.isBlank()) return this

    return Uri.encode(this, null)
}

fun String.urlDecode(): String {
    if(this.isBlank()) return this

    return Uri.decode(this)
}