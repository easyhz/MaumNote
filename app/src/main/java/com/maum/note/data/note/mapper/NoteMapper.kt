package com.maum.note.data.note.mapper

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.common.util.gpt.GptRole
import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.database.note.entity.NoteWithStudent
import com.maum.note.core.firebase.configuration.model.response.SystemPromptResponse
import com.maum.note.core.model.note.AgeType
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.request.etc.InputRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import com.maum.note.data.note.model.NoteGenerationMapParam
import com.maum.note.data.note.model.InputRequestMapParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import com.maum.note.domain.note.model.response.NoteResponse
import java.time.LocalDateTime
import javax.inject.Inject

class NoteMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val appDateTimeFormatter: AppDateTimeFormatter
) {
    fun mapToNoteEntity(noteRequestParam: NoteRequestParam): NoteEntity = NoteEntity(
        noteType = noteRequestParam.noteType,
        age = noteRequestParam.age,
        sentenceCount = noteRequestParam.sentenceCount,
        inputContent = noteRequestParam.inputContent,
        result = noteRequestParam.result,
        createdAt = LocalDateTime.now().toString(),
        updatedAt = LocalDateTime.now().toString(),
        isDeleted = false
    )

    fun mapToCreateNoteRequest(
        param: NoteGenerationMapParam,
    ): GptRequest =
        GptRequest(
            model = resourceHelper.getString(R.string.model),
            input = createGptInputRequests(
                param = param.toInputRequestMapParam()
            )
        )

    fun mapToNoteGenerationResponse(
        response: GptResponse
    ) = NoteGenerationResponse(
        status = response.status,
        result = response.output.getOrNull(0)?.content?.getOrNull(0)?.text ?: "",
    )

    fun mapToNoteResponse(
        noteWithStudent: NoteWithStudent
    ) = NoteResponse(
        id = noteWithStudent.note.id,
        noteType = noteWithStudent.note.noteType,
        ageType = noteWithStudent.note.age,
        sentenceCountType = noteWithStudent.note.sentenceCount,
        inputContent = noteWithStudent.note.inputContent,
        result = noteWithStudent.note.result,
        createdAt = appDateTimeFormatter.convertStringToDateTime(noteWithStudent.note.createdAt),
    )

    private fun createGptInputRequests(
        param: InputRequestMapParam,
    ): List<InputRequest> {
        val systemPrompt = InputRequest(
            role = GptRole.SYSTEM.alias,
            content = resolveSystemPrompt(
                noteType = param.noteType,
                systemPrompt = param.systemPrompt
            )
        )

        val userPrompt = InputRequest(
            role = GptRole.USER.alias,
            content = getUserPrompt(
                param = param
            )
        )
        return listOf(systemPrompt, userPrompt)
    }

    private fun resolveSystemPrompt(
        noteType: NoteType,
        systemPrompt: SystemPromptResponse?
    ): String {
        return systemPrompt?.getPromptFor(noteType).takeUnless { it.isNullOrBlank() }
            ?: resourceHelper.getString(noteType.toPromptStringRes())
    }

    private fun SystemPromptResponse.getPromptFor(noteType: NoteType): String? {
        return when (noteType) {
            NoteType.LETTER_GREETING -> letterGreetingPrompt
            NoteType.ANNOUNCEMENT_CONTENT -> announcementContentPrompt
            NoteType.PLAY_CONTEXT -> playContextPrompt
            else -> letterGreetingPrompt
        }
    }

    private fun NoteType.toPromptStringRes(): Int = when (this) {
        NoteType.LETTER_GREETING -> R.string.system_prompt_letter_greeting
        NoteType.ANNOUNCEMENT_CONTENT -> R.string.system_prompt_announcement_content
        NoteType.PLAY_CONTEXT -> R.string.system_prompt_play_context
        else -> R.string.system_prompt_default
    }

    private fun AgeType.toPromptString(systemPrompt: SystemPromptResponse): String {
        return when (this) {
            AgeType.ZERO -> systemPrompt.age0Desc
            AgeType.ONE -> systemPrompt.age1Desc
            AgeType.TWO -> systemPrompt.age2Desc
            AgeType.THREE -> systemPrompt.age3Desc
            AgeType.FOUR -> systemPrompt.age4Desc
            AgeType.FIVE -> systemPrompt.age5Desc
            AgeType.MIXED -> systemPrompt.ageMixedDesc
        }
    }

    private fun getAgePrompt(ageType: AgeType, systemPrompt: SystemPromptResponse?): String {
        val age = resourceHelper.getString(ageType.title)
        return when (val prompt = systemPrompt) {
            null -> age
            else -> "$age, ${ageType.toPromptString(prompt)}"
        }
    }

    private fun getUserPrompt(
        param: InputRequestMapParam,
    ): String {
        val age = getAgePrompt(param.ageType, param.systemPrompt)
        val sentence = resourceHelper.getString(param.sentenceType.title)
        val note = resourceHelper.getString(param.noteType.title)
        val inputContent = param.inputContent
        val defaultTone = param.defaultTone
        val typeTone = param.typeTone

        return resourceHelper.getString(
            R.string.user_prompt,
            age,
            sentence,
            inputContent,
            defaultTone,
            note,
            typeTone
        )
    }
}