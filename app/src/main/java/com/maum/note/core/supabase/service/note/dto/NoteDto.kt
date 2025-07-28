package com.maum.note.core.supabase.service.note.dto

import com.maum.note.core.common.util.Generate
import com.maum.note.core.supabase.constant.Table
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    @SerialName(Table.NOTES.ID)
    val id: String? = Generate.randomUUIDv7(),
    @SerialName(Table.NOTES.USER_ID)
    val userId: String,
    @SerialName(Table.NOTES.STUDENT_ID)
    val studentId: Int? = null,
    @SerialName(Table.NOTES.NOTE_TYPE)
    val noteType: String,
    @SerialName(Table.NOTES.STUDENT_AGE)
    val studentAge: String,
    @SerialName(Table.NOTES.SENTENCE_COUNT)
    val sentenceCount: String,
    @SerialName(Table.NOTES.INPUT_CONTENT)
    val inputContent: String,
    @SerialName(Table.NOTES.RESULT)
    val result: String,
    @SerialName(Table.NOTES.CREATED_AT)
    val createdAt: Instant,
    @SerialName(Table.NOTES.UPDATED_AT)
    val updatedAt: Instant = Clock.System.now(),
    @SerialName(Table.NOTES.IS_DELETED)
    val isDeleted: Boolean = false
)
