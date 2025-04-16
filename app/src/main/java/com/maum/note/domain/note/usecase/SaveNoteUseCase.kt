package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.model.request.NoteRequest
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseUseCase<NoteRequest, Unit>() {
    override suspend fun invoke(param: NoteRequest): Result<Unit> {
        return runCatching {
            noteRepository.saveNote(param)
        }
    }
}