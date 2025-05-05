package com.maum.note.data.note.mapper

import com.maum.note.R
import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.core.common.util.gpt.GptRole
import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.network.gpt.model.note.request.GptRequest
import com.maum.note.core.network.gpt.model.note.request.etc.InputRequest
import com.maum.note.core.network.gpt.model.note.response.GptResponse
import com.maum.note.data.note.model.NoteGenerationMapParam
import com.maum.note.data.note.model.InputRequestMapParam
import com.maum.note.domain.note.model.request.NoteRequestParam
import com.maum.note.domain.note.model.response.NoteGenerationResponse
import java.time.LocalDateTime
import javax.inject.Inject

class NoteMapper @Inject constructor(
    private val resourceHelper: ResourceHelper,
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

    private fun createGptInputRequests(
        param: InputRequestMapParam,
    ): List<InputRequest> {
        val systemPrompt = InputRequest(
            role = GptRole.SYSTEM.alias,
            content = getSystemPrompt(noteType = param.noteType)
        )
        val userPrompt = InputRequest(
            role = GptRole.USER.alias,
            content = getUserPrompt(
                param = param
            )
        )
        return listOf(systemPrompt, userPrompt)
    }

    private fun getSystemPrompt(
        noteType: NoteType
    ): String {
        val id = when (noteType) {
            NoteType.LETTER_GREETING -> R.string.system_prompt_letter_greeting
            NoteType.ANNOUNCEMENT_CONTENT -> R.string.system_prompt_announcement_content
            NoteType.PLAY_CONTEXT -> R.string.system_prompt_play_context
            else -> R.string.system_prompt_default
        }
        return resourceHelper.getString(id)
    }

    private fun getUserPrompt(
        param: InputRequestMapParam,
    ): String {
        val age = resourceHelper.getString(param.ageType.title)
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