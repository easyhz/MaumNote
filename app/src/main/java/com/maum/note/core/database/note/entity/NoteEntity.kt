package com.maum.note.core.database.note.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "NOTE",
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val createdAt: String, // LocalDateTime
    val updatedAt: String, // LocalDateTime
    val isDeleted: Boolean,
)
