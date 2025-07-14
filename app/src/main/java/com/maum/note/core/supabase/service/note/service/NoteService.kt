package com.maum.note.core.supabase.service.note.service

import com.maum.note.core.supabase.service.note.dto.NoteDto

interface NoteService {
    suspend fun insertNote(noteDto: NoteDto)
}