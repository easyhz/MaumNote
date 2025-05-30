package com.maum.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.note.entity.NoteEntity
import com.maum.note.core.database.student.entity.StudentEntity
import com.maum.note.core.database.tone.dao.ToneDao
import com.maum.note.core.database.tone.entity.ToneEntity

@Database(
    entities = [NoteEntity::class, ToneEntity::class, StudentEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val toneDao: ToneDao
}