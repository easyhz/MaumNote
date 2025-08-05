package com.maum.note.core.supabase.service.tone.service

import com.maum.note.core.supabase.service.tone.dto.ToneDto

interface ToneService {
    suspend fun insertTone(toneDto: ToneDto)

    suspend fun fetchTone(userId: String): ToneDto?
    suspend fun upsertTone(toneDto: ToneDto)
}