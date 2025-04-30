package com.maum.note.core.database.tone.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @param id
 * @param noteType [NoteType] name - 톤 종류
 * @param content - 내용
 * @param isSelected - 선택 여부
 * @param createdAt - 생성일
 * @param updatedAt - 수정일
 */
@Entity(
    tableName = "TONE",
    indices = [Index(value = ["noteType", "isSelected"], unique = true)]
)
data class ToneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteType: String, // NoteType
    val content: String,
    val isSelected: Boolean,
    val createdAt: String, // LocalDateTime
    val updatedAt: String, // LocalDateTime
)
