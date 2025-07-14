package com.maum.note.core.supabase.service.note.service

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.note.dto.NoteDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class NoteServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : NoteService{
    override suspend fun insertNote(noteDto: NoteDto) {
        postgrest.from(Table.NOTES.name).insert(noteDto)
    }
}