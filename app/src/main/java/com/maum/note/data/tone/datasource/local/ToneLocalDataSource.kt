package com.maum.note.data.tone.datasource.local

import com.maum.note.core.database.tone.entity.ToneEntity

interface ToneLocalDataSource {
    suspend fun findAllSelectedTones(): List<ToneEntity>
    suspend fun findByNoteType(noteType: String): ToneEntity?
    suspend fun updateTone(noteType: String, content: String)
}