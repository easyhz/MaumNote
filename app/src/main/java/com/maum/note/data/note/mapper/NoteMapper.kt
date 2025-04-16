package com.maum.note.data.note.mapper

import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.data.note.model.request.NoteRequestData
import com.maum.note.domain.note.model.request.NoteRequest
import java.time.LocalDateTime

fun NoteRequestData.toEntity() = NoteEntity(
    content = content,
    createdAt = LocalDateTime.now().toString(),
    updatedAt = LocalDateTime.now().toString(),
    isDeleted = false,
)

fun NoteRequest.toRequestData() = NoteRequestData(
    content = content,
)