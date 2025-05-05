package com.maum.note.core.common.helper.log

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}