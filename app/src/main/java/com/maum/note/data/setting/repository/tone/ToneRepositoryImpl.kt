package com.maum.note.data.setting.repository.tone

import com.maum.note.core.common.error.AppError
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.data.setting.datasource.tone.remote.ToneRemoteDataSource
import com.maum.note.data.setting.mapper.tone.ToneMapper
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.domain.setting.model.tone.request.InsertToneRequestParam
import com.maum.note.domain.setting.model.tone.response.ToneResponse
import com.maum.note.domain.setting.model.tone.response.ToneResponseResult
import com.maum.note.domain.setting.repository.tone.ToneRepository
import javax.inject.Inject

class ToneRepositoryImpl @Inject constructor(
    private val toneMapper: ToneMapper,
    private val toneLocalDataSource: ToneLocalDataSource,
    private val toneRemoteDataSource: ToneRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
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

    override suspend fun updateTone(param: InsertToneRequestParam) {
        toneRemoteDataSource.upsertTone(
            toneDto = toneMapper.mapToToneDto(param = param),
        )
    }

    override suspend fun insertTone(param: InsertToneRequestParam) {
        toneRemoteDataSource.insertTone(toneMapper.mapToToneDto(param = param))
    }

    override suspend fun fetchTone(): Result<ToneResponse> = runCatching {
        val id = userRemoteDataSource.getCurrentUser()?.id ?: throw AppError.NoUserDataError

        val toneDto = toneRemoteDataSource.fetchTone(userId = id) ?: return@runCatching ToneResponse.empty(userId = id)
        toneMapper.mapToToneResponse(toneDto)
    }
}