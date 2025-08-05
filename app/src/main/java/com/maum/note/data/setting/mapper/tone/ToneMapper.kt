package com.maum.note.data.setting.mapper.tone

import com.maum.note.core.common.util.Generate
import com.maum.note.core.database.tone.entity.ToneEntity
import com.maum.note.core.supabase.service.tone.dto.ToneDto
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import com.maum.note.domain.setting.model.tone.response.ToneResponse
import com.maum.note.domain.setting.model.tone.response.ToneResponseResult
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

    fun mapToToneDto(
        param: InsertToneRequestParam
    ): ToneDto = ToneDto(
        id = param.id ?: Generate.randomUUIDv7(),
        userId = param.userId,
        common = param.common,
        letterGreeting = param.letterGreeting,
        playContext = param.playContext,
        announcementContent = param.announcementContent,
    )

    fun mapToToneResponse(
        param: ToneDto
    ): ToneResponse = ToneResponse(
        id = param.id,
        userId = param.userId,
        common = param.common,
        letterGreeting = param.letterGreeting,
        playContext = param.playContext,
        announcementContent = param.announcementContent
    )
}