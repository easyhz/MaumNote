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
import com.maum.note.core.model.note.SentenceType
import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.request.etc.InputRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import com.maum.note.core.supabase.service.note.dto.NoteDto
import com.maum.note.data.note.model.InputRequestMapParam
import com.maum.note.data.note.model.InsertNoteParam
import com.maum.note.data.note.model.NoteGenerationMapParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import com.maum.note.domain.note.model.response.NoteResponse
import kotlinx.datetime.Clock
import java.time.LocalDateTime
import javax.inject.Inject

class NoteMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
) {
    /**
     * NoteRequestParam -> NoteEntity
     */
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

    /**
     * NoteGenerationMapParam -> GptRequest
     */
    fun mapToCreateNoteRequest(
        param: NoteGenerationMapParam,
    ): GptRequest =
        GptRequest(
            model = resourceHelper.getString(R.string.model),
            input = createGptInputRequests(
                param = param.toInputRequestMapParam()
            )
        )

    /**
     * GptResponse -> NoteGenerationResponse
     */
    fun mapToNoteGenerationResponse(
        response: GptResponse
    ) = NoteGenerationResponse(
        status = response.status,
        result = response.output.getOrNull(0)?.content?.getOrNull(0)?.text ?: "",
    )

    /**
     * NoteWithStudent -> NoteResponse
     */
    fun mapToNoteResponse(
        noteWithStudent: NoteWithStudent
    ) = NoteResponse(
        id = noteWithStudent.note.id,
        noteType = noteWithStudent.note.noteType,
        ageType = noteWithStudent.note.age,
        sentenceCountType = noteWithStudent.note.sentenceCount,
        inputContent = noteWithStudent.note.inputContent,
        result = noteWithStudent.note.result,
        createdAt = AppDateTimeFormatter.convertStringToDateTime(noteWithStudent.note.createdAt),
    )

    fun mapToNoteDto(
        insertNoteParam: InsertNoteParam
    ): NoteDto {
        val noteRequestParam = insertNoteParam.param
        val noteType = NoteType.getByValue(noteRequestParam.noteType) ?: NoteType.PLAY_CONTEXT
        val ageType = AgeType.getByValue(noteRequestParam.age) ?: AgeType.MIXED
        val sentenceType = SentenceType.getByValue(noteRequestParam.sentenceCount) ?: SentenceType.FOUR_TO_FIVE
        val userId = insertNoteParam.userId

        return NoteDto(
            userId = userId,
            noteType = noteType.alias,
            studentAge = ageType.alias,
            sentenceCount = sentenceType.alias,
            inputContent = noteRequestParam.inputContent,
            result = noteRequestParam.result,
            creationTime = Clock.System.now(),
            updatedTime = Clock.System.now(),
            isDeleted = false
        )
    }

    /**
     * GPT input 생성
     */
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

    /**
     * 시스템 프롬프트 생성
     */
    private fun resolveSystemPrompt(
        noteType: NoteType,
        systemPrompt: SystemPromptResponse?
    ): String {
        return systemPrompt?.getPromptFor(noteType).takeUnless { it.isNullOrBlank() }
            ?: resourceHelper.getString(noteType.toPromptStringRes())
    }

    /**
     * 시스템 프롬프트 가져오기
     */
    private fun SystemPromptResponse.getPromptFor(noteType: NoteType): String? {
        return when (noteType) {
            NoteType.LETTER_GREETING -> letterGreetingPrompt
            NoteType.ANNOUNCEMENT_CONTENT -> announcementContentPrompt
            NoteType.PLAY_CONTEXT -> playContextPrompt
            else -> letterGreetingPrompt
        }
    }

    /**
     * NoteType에 따른 프롬프트 리소스 가져오기
     */
    private fun NoteType.toPromptStringRes(): Int = when (this) {
        NoteType.LETTER_GREETING -> R.string.system_prompt_letter_greeting
        NoteType.ANNOUNCEMENT_CONTENT -> R.string.system_prompt_announcement_content
        NoteType.PLAY_CONTEXT -> R.string.system_prompt_play_context
        else -> R.string.system_prompt_default
    }

    /**
     * AgeType에 따른 프롬프트 문자열 가져오기
     */
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

    /**
     * AgeType 프롬프트 조합
     */
    private fun getAgePrompt(ageType: AgeType, systemPrompt: SystemPromptResponse?): String {
        val age = resourceHelper.getString(ageType.title)
        return when (val prompt = systemPrompt) {
            null -> age
            else -> "$age, ${ageType.toPromptString(prompt)}"
        }
    }

    /**
     * 사용자 프롬프트 가져오기
     */
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