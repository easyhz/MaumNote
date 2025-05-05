package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.model.request.CreateNoteRequestParam
import com.maum.note.domain.note.model.response.CreateNoteResponse
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class GenerateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseUseCase<CreateNoteRequestParam, CreateNoteResponse>() {
    override suspend fun invoke(param: CreateNoteRequestParam): Result<CreateNoteResponse> {
        return noteRepository.generateNote(param)
    }
}