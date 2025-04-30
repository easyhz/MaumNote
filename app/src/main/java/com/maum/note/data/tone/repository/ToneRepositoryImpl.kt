package com.maum.note.data.tone.repository

import com.maum.note.data.tone.datasource.local.ToneLocalDataSource
import com.maum.note.data.tone.mapper.ToneMapper
import com.maum.note.domain.tone.model.request.UpdateToneRequestParam
import com.maum.note.domain.tone.model.response.ToneResponseResult
import com.maum.note.domain.tone.repository.ToneRepository
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