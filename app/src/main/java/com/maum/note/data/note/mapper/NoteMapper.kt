package com.maum.note.data.note.mapper

import com.maum.note.core.common.error.AppError
import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.model.note.SentenceType
import com.maum.note.data.note.model.request.NoteRequestData
import com.maum.note.domain.note.model.request.NoteRequest
import java.time.LocalDateTime

fun NoteRequestData.toEntity() = NoteEntity(
    noteType = noteType.name,
    age = age.name,
    sentenceCount = sentenceCount.name,
    inputContent = inputContent,
    result = result,
    createdAt = LocalDateTime.now().toString(),
    updatedAt = LocalDateTime.now().toString(),
    isDeleted = false,
)

fun NoteRequest.toRequestData() = NoteRequestData(
    noteType = NoteType.getByValue(noteType) ?: throw AppError.DefaultError("Invalid note type"),
    age = AgeType.getByValue(age) ?: throw AppError.DefaultError("Invalid age type"),
    sentenceCount = SentenceType.getByValue(sentenceCount) ?: throw AppError.DefaultError("Invalid sentence count"),
    inputContent = inputContent,
    result = result,
)