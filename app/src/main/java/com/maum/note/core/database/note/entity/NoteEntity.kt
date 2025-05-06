package com.maum.note.core.database.note.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param id
 * @param noteType [NoteType] name - 노트 종류
 * @param age [AgeType] name - 연령
 * @param sentenceCount [SentenceType] name - 문장 수
 * @param inputContent - 입력 내용
 * @param result - 결과
 * @param createdAt - 생성일
 * @param updatedAt - 수정일
 * @param isDeleted - 삭제 여부
 */
@Entity(
    tableName = "NOTE",
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val noteType: String, // NoteType
    val age: String, // AgeType
    val sentenceCount: String, // SentenceType
    val inputContent: String,
    val result: String,
    val createdAt: String, // LocalDateTime
    val updatedAt: String, // LocalDateTime
    val studentId: Long? = null,
    val isDeleted: Boolean,
)
