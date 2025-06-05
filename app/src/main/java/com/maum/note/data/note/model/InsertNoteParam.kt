package com.maum.note.data.note.model

import com.maum.note.domain.note.model.request.NoteRequestParam

data class InsertNoteParam(
    val userId: String,
    val studentId: String? = null,
    val param: NoteRequestParam
)
