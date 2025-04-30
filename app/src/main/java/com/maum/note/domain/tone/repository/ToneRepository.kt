package com.maum.note.domain.tone.repository

import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.model.response.ToneResponseResult

interface ToneRepository {
    suspend fun getAllSelectedTones(): List<ToneResponseResult>
    suspend fun getToneByNoteType(noteType: String): ToneResponseResult?
    suspend fun updateTone(updateToneRequestParam: UpdateToneRequestParam)
}