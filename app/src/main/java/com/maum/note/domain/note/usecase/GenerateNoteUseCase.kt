package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.response.NoteResponse
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class GenerateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseUseCase<NoteGenerationRequestParam, NoteResponse>() {
    override suspend fun invoke(param: NoteGenerationRequestParam): Result<NoteResponse> {
        return noteRepository.generateNote(param)
    }
}