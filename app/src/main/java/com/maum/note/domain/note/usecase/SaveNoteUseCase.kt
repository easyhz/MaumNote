package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) : BaseUseCase<NoteRequestParam, Unit>() {
    override suspend fun invoke(param: NoteRequestParam): Result<Unit> {
        return runCatching {
            noteRepository.saveNote(param)
        }
    }
}