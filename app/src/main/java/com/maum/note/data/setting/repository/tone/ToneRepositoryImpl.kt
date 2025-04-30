package com.maum.note.data.setting.repository.tone

import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.data.setting.mapper.tone.ToneMapper
import com.maum.note.domain.setting.model.tone.request.UpdateToneRequestParam
import com.maum.note.domain.setting.model.tone.response.ToneResponseResult
import com.maum.note.domain.setting.repository.tone.ToneRepository
import javax.inject.Inject

class ToneRepositoryImpl @Inject constructor(
    private val toneMapper: ToneMapper,
    private val toneLocalDataSource: ToneLocalDataSource
): ToneRepository {
    override suspend fun getAllSelectedTones(): List<ToneResponseResult> {
        return toneLocalDataSource.findAllSelectedTones()
            .map { tone ->
                toneMapper.mapToToneResponseResult(
                    toneEntity = tone
                )
            }
    }

    override suspend fun getToneByNoteType(noteType: String): ToneResponseResult? {
        return toneLocalDataSource.findByNoteType(noteType = noteType)
            ?.let { tone ->
                toneMapper.mapToToneResponseResult(
                    toneEntity = tone
                )
            }
    }

    override suspend fun updateTone(updateToneRequestParam: UpdateToneRequestParam) {
        toneLocalDataSource.updateTone(
            noteType = updateToneRequestParam.noteType,
            content = updateToneRequestParam.content
        )
    }
}