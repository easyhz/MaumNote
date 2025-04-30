package com.maum.note.data.tone.mapper

import com.maum.note.core.database.tone.entity.ToneEntity
import com.maum.note.domain.tone.model.response.ToneResponseResult
import javax.inject.Inject

class ToneMapper @Inject constructor(

) {
    fun mapToToneResponseResult(
        toneEntity: ToneEntity,
    ): ToneResponseResult {
        return ToneResponseResult(
            noteType = toneEntity.noteType,
            content = toneEntity.content
        )
    }
}