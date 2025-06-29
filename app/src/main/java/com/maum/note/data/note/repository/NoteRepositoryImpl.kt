package com.maum.note.data.note.repository

import android.util.Log
import com.maum.note.core.common.error.AppError
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.database.note.entity.NoteWithStudent
import com.maum.note.core.model.note.NoteType
import com.maum.note.data.configuration.datasource.remote.ConfigurationRemoteDataSource
import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSource
import com.maum.note.data.note.mapper.NoteMapper
import com.maum.note.data.note.model.InsertNoteParam
import com.maum.note.data.note.model.NoteGenerationMapParam
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.domain.note.model.request.NoteGenerationRequestParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import com.maum.note.domain.note.model.response.NoteResponse
import com.maum.note.domain.note.repository.NoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val logger: Logger,
    private val noteMapper: NoteMapper,
    private val toneLocalDataSource: ToneLocalDataSource,
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : NoteRepository {
    override suspend fun saveNote(request: NoteRequestParam) {
        noteLocalDataSource.saveNote(
            data = noteMapper.mapToNoteEntity(request)
        )
    }

    override suspend fun generateNote(param: NoteGenerationRequestParam): Result<NoteResponse> = runCatching {
        coroutineScope {
            val userId = userRemoteDataSource.getCurrentUser()?.id ?: throw AppError.NoUserDataError
            val defaultToneDeferred =
                async { toneLocalDataSource.findByNoteType(NoteType.DEFAULT.name) }
            val typeToneDeferred = async { toneLocalDataSource.findByNoteType(param.noteType) }
            val systemPromptDeferred = async { configurationRemoteDataSource.fetchSystemPrompt() }

            val defaultTone = defaultToneDeferred.await()
            val typeTone = typeToneDeferred.await()
            val systemPrompt = systemPromptDeferred.await().getOrNull()

            val noteGenerationMapParam = NoteGenerationMapParam(
                noteGenerationRequestParam = param,
                defaultTone = defaultTone?.content ?: "",
                typeTone = typeTone?.content ?: "",
                systemPrompt = systemPrompt
            )

            val generateNoteRequest = noteMapper.mapToCreateNoteRequest(noteGenerationMapParam)
            Log.d("NoteRepositoryImpl", "generateNoteRequest: $generateNoteRequest")

            val response = noteRemoteDataSource.generateNote(request = generateNoteRequest).getOrThrow()
            val result = noteMapper.mapToNoteGenerationResponse(response)
            insertNote(userId = userId, request = param, result = result)
            val noteEntity = saveNote(request = param, result = result)
            noteMapper.mapToNoteResponse(noteEntity)
        }
    }

    override fun findAllNotesFlow(): Flow<List<NoteResponse>> {
        return noteLocalDataSource.findAllNotesFlow()
            .map { noteEntities ->
                noteEntities.map { noteEntity ->
                    noteMapper.mapToNoteResponse(noteEntity)
                }
            }
    }

    override suspend fun countNotes(): Int {
        return noteLocalDataSource.countNotes()
    }

    private suspend fun insertNote(
        userId: String,
        request: NoteGenerationRequestParam,
        result: NoteGenerationResponse
    ): Result<Unit> = runCatching {
        val param = InsertNoteParam(
            userId = userId,
            param = NoteRequestParam(
                noteType = request.noteType,
                age = request.ageType,
                sentenceCount = request.sentenceCount,
                inputContent = request.inputContent,
                result = result.result
            )
        )

        val note = noteMapper.mapToNoteDto(param)
        noteRemoteDataSource.insertNote(note)
    }

    private suspend fun saveNote(
        request: NoteGenerationRequestParam,
        result: NoteGenerationResponse,
    ): NoteWithStudent {
        val note = noteMapper.mapToNoteEntity(
            noteRequestParam = NoteRequestParam(
                noteType = request.noteType,
                age = request.ageType,
                sentenceCount = request.sentenceCount,
                inputContent = request.inputContent,
                result = result.result
            )
        )
        val entity = NoteWithStudent(
            note = note,
            student = null
        )
        try {
            return noteLocalDataSource.insertAndGetNote(entity)
        } catch (e: Exception) {
            logger.e("NoteRepositoryImpl", "Error saving note to local database", e)
        }

        return entity
    }
}