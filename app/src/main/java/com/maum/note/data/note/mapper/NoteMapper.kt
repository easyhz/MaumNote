package com.maum.note.data.note.mapper

import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.domain.note.model.request.NoteRequestParam
import java.time.LocalDateTime

class NoteMapper {
    fun mapToNoteEntity(noteRequestParam: NoteRequestParam): NoteEntity = NoteEntity(
        noteType = noteRequestParam.noteType,
        age = noteRequestParam.age,
        sentenceCount = noteRequestParam.sentenceCount,
        inputContent = noteRequestParam.inputContent,
        result = noteRequestParam.result,
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString(),
        isDeleted = false
    )
}