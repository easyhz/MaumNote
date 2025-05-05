package com.maum.note.data.note.repository

import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.model.note.NoteType
import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSource
import com.maum.note.data.note.mapper.NoteMapper
import com.maum.note.data.note.model.CreateNoteMapParam
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.domain.note.model.request.CreateNoteRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.CreateNoteResponse
import com.maum.note.domain.note.repository.NoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val logger: Logger,
    private val noteMapper: NoteMapper,
    private val toneLocalDataSource: ToneLocalDataSource,
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
) : NoteRepository {
    override suspend fun saveNote(request: NoteRequestParam) {
        noteLocalDataSource.saveNote(
            data = noteMapper.mapToNoteEntity(request)
        )
    }

    override suspend fun createNote(request: CreateNoteRequestParam): Result<CreateNoteResponse> = coroutineScope {
        val defaultToneDeferred = async { toneLocalDataSource.findByNoteType(NoteType.DEFAULT.name) }
        val typeToneDeferred = async { toneLocalDataSource.findByNoteType(request.noteType) }

        val defaultTone = defaultToneDeferred.await()
        val typeTone = typeToneDeferred.await()

        return@coroutineScope noteRemoteDataSource.createNote(
            noteMapper.mapToCreateNoteRequest(
                param = CreateNoteMapParam(
                    createNoteRequestParam = request,
                    defaultTone = defaultTone?.content ?: "",
                    typeTone = typeTone?.content ?: ""
                )
            )
        ).map { response ->
            val result = noteMapper.mapToCreateNoteResponse(response)
            saveNote(request = request, result = result)

            result
        }
    }

    private suspend fun saveNote(
        request: CreateNoteRequestParam,
        result: CreateNoteResponse,
    ) {
        val entity = noteMapper.mapToNoteEntity(
            noteRequestParam = NoteRequestParam(
                noteType = request.noteType,
                age = request.age,
                sentenceCount = request.sentenceCount,
                inputContent = request.inputContent,
                result = result.result
            )
        )
        try {
            noteLocalDataSource.saveNote(entity)
        } catch (e: Exception) {
            logger.e("NoteRepositoryImpl", "Error saving note to local database", e)
        }
    }
}