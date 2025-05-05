package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class GenerateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseUseCase<NoteGenerationRequestParam, NoteGenerationResponse>() {
    override suspend fun invoke(param: NoteGenerationRequestParam): Result<NoteGenerationResponse> {
        return noteRepository.generateNote(param)
    }
}