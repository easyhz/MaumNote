package com.maum.note.data.setting.datasource.tone.remote

import com.maum.note.core.supabase.service.tone.dto.ToneDto
import com.maum.note.core.supabase.service.tone.service.ToneService
import javax.inject.Inject

class ToneRemoteDataSourceImpl @Inject constructor(
    private val toneService: ToneService
): ToneRemoteDataSource {
    override suspend fun insertTone(toneDto: ToneDto) {
        toneService.insertTone(toneDto)
    }
}