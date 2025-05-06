package com.maum.note.core.common.util.version

import com.maum.note.BuildConfig

class Version {
    fun needsUpdate(newVersion: String, currentVersion: String = BuildConfig.VERSION_NAME): Boolean {
        val newVersions = newVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val currentVersions = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }

        return newVersions.zip(currentVersions) { x, y -> x.compareTo(y) }
            .firstOrNull { it != 0 }?.let { it > 0 } ?: false
    }
}