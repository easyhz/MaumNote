package com.maum.note.domain.note.usecase

import com.maum.note.domain.note.repository.NoteRepository
import javax.inject.Inject

class FetchPagedNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() = noteRepository.fetchPagedNotes()
}