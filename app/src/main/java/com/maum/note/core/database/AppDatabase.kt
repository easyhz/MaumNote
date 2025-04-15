package com.maum.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.note.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}