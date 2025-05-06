package com.maum.note.core.database.student.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @param id
 * @param name - 학생 이름
 * @param content - 학생 내용
 */
@Entity(
    tableName = "STUDENT",
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val content: String
)