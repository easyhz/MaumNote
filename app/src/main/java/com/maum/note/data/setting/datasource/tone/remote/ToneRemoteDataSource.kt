package com.maum.note.data.setting.datasource.tone.remote

import com.maum.note.core.supabase.service.tone.dto.ToneDto

interface ToneRemoteDataSource {
    suspend fun insertTone(toneDto: ToneDto)
}