package com.maum.note.core.database.note.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.maum.note.core.database.student.entity.StudentEntity

data class NoteWithStudent(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "id"
    )
    val student: StudentEntity?
)
