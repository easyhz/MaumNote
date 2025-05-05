package com.maum.note.domain.note.usecase

import com.maum.note.domain.note.model.response.NoteResponse
import com.maum.note.domain.note.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    fun invoke(): Flow<List<NoteResponse>> {
        return noteRepository.findAllNotesFlow()
    }
}