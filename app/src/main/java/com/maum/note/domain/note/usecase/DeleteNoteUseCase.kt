package com.maum.note.domain.note.usecase

import com.maum.note.core.common.base.BaseUseCase
import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
): BaseUseCase<String, Unit>() {
    override suspend fun invoke(param: String): Result<Unit> {
        return noteRepository.deleteNote(noteId = param)
    }
}