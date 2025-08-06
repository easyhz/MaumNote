package com.maum.note.core.supabase.service.note.service

import com.maum.note.core.supabase.constant.Table
import com.maum.note.core.supabase.service.note.dto.NoteDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Order
import javax.inject.Inject

class NoteServiceImpl @Inject constructor(
    private val postgrest: Postgrest
) : NoteService{
    override suspend fun insertNote(noteDto: NoteDto) {
        postgrest.from(Table.NOTES.name).insert(noteDto)
    }

    override suspend fun fetchNotes(
        userId: String,
        from: Long,
        to: Long
    ): List<NoteDto> {
        return postgrest.from(Table.NOTES.name).select {
            filter {
                eq(Table.NOTES.USER_ID, userId)
                eq(Table.NOTES.IS_DELETED, false)
            }
            order(Table.NOTES.CREATED_AT, Order.DESCENDING)
            range(from = from, to = to)
        }.decodeList()
    }

    override suspend fun deleteNote(noteId: String) {
        postgrest.from(Table.NOTES.name).update(
            {
                set(Table.NOTES.IS_DELETED, true)
            }
        ) {
            filter {
                eq(Table.NOTES.ID, noteId)
            }
        }
    }
}