package com.maum.note.core.supabase.service.tone.service

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.tone.dto.ToneDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class ToneServiceImpl @Inject constructor(
    private val postgrest: Postgrest
): ToneService {
    override suspend fun insertTone(toneDto: ToneDto) {
        postgrest.from(Table.TONES.name).insert(toneDto)
    }

    override suspend fun fetchTone(userId: String): ToneDto? {
        return postgrest.from(Table.TONES.name).select {
            filter {
                eq(Table.TONES.USER_ID, userId)
            }
        }.decodeSingle()
    }

    override suspend fun upsertTone(toneDto: ToneDto) {
        postgrest.from(Table.TONES.name).upsert(toneDto)
    }
}