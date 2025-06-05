package com.maum.note.core.supabase.note.service

import com.maum.note.core.supabase.note.dto.NoteDto

interface NoteService {
    suspend fun insertNote(noteDto: NoteDto)
}