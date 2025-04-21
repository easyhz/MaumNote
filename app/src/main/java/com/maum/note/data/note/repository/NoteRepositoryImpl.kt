package com.maum.note.data.note.repository

import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.mapper.toRequestData
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource,
): NoteRepository {
    override suspend fun saveNote(request: NoteRequestParam) {
        noteLocalDataSource.saveNote(
            data = request.toRequestData()
        )
    }
}