package com.maum.note.core.common.util

import com.github.f4b6a3.uuid.UuidCreator

object Generate {
    fun randomUUIDv7() = UuidCreator.getTimeOrderedEpoch().toString()
}
