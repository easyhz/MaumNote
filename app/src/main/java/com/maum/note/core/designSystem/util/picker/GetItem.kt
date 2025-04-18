package com.maum.note.core.designSystem.util.picker

fun <T> getItem(items: List<T>, index: Int) = items[index % items.size]