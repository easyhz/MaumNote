package com.maum.note.domain.note.usecase

import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class GetPagedNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() = noteRepository.getPagedNotes()
}