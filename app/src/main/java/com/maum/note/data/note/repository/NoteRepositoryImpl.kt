package com.maum.note.data.note.repository

import android.util.Log
import com.maum.note.core.common.helper.log.Logger
import com.maum.note.core.database.note.entity.NoteWithStudent
import com.maum.note.core.model.note.NoteType
import com.maum.note.data.configuration.datasource.ConfigurationRemoteDataSource
import com.maum.note.data.note.datasource.local.NoteLocalDataSource
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSource
import com.maum.note.data.note.mapper.NoteMapper
import com.maum.note.data.note.model.NoteGenerationMapParam
import com.maum.note.data.setting.datasource.age.local.AgeLocalDataSource
import com.maum.note.data.setting.datasource.tone.local.ToneLocalDataSource
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
    private val ageLocalDataSource: AgeLocalDataSource,
    private val toneLocalDataSource: ToneLocalDataSource,
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource,
) : NoteRepository {
    override suspend fun saveNote(request: NoteRequestParam) {
        noteLocalDataSource.saveNote(
            data = noteMapper.mapToNoteEntity(request)
        )
    }

    override suspend fun generateNote(param: NoteGenerationRequestParam): Result<NoteResponse> = runCatching {
        coroutineScope {
            val defaultToneDeferred =
                async { toneLocalDataSource.findByNoteType(NoteType.DEFAULT.name) }
            val typeToneDeferred = async { toneLocalDataSource.findByNoteType(param.noteType) }
            val ageTypeDeferred = async { ageLocalDataSource.getAgeSetting() }
            val configuration = async { configurationRemoteDataSource.fetchConfiguration() }

            val defaultTone = defaultToneDeferred.await()
            val typeTone = typeToneDeferred.await()
            val ageType = ageTypeDeferred.await()
            val config = configuration.await().getOrNull()
            val request = param.copy(
                ageType = ageType
            )

            val noteGenerationMapParam = NoteGenerationMapParam(
                noteGenerationRequestParam = request.copy(
                    ageType = ageType
                ),
                defaultTone = defaultTone?.content ?: "",
                typeTone = typeTone?.content ?: "",
                configuration = config
            )

            val generateNoteRequest = noteMapper.mapToCreateNoteRequest(noteGenerationMapParam)
            Log.d("NoteRepositoryImpl", "generateNoteRequest: $generateNoteRequest")

            val response = noteRemoteDataSource.generateNote(request = generateNoteRequest).getOrThrow()
            val result = noteMapper.mapToNoteGenerationResponse(response)
            val noteEntity = saveNote(request = request.copy(ageType = ageType), result = result)
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