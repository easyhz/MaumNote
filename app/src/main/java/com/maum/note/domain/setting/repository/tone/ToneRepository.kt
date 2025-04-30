package com.maum.note.domain.setting.repository.tone

import com.maum.note.domain.setting.model.tone.request.UpdateToneRequestParam
import com.maum.note.domain.setting.model.tone.response.ToneResponseResult

interface ToneRepository {
    suspend fun getAllSelectedTones(): List<ToneResponseResult>
    suspend fun getToneByNoteType(noteType: String): ToneResponseResult?
    suspend fun updateTone(updateToneRequestParam: UpdateToneRequestParam)
}