package com.maum.note.core.database.util.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *  나중에 학생이 추가될 계획을 가지고 있어서, 미리 nullable 하게 추가해 둠.
 *
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // NOTE 테이블에 studentId 컬럼 추가
        db.execSQL("ALTER TABLE NOTE ADD COLUMN studentId INTEGER")

        // STUDENT 테이블 생성
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS STUDENT (
                id INTEGER NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                content TEXT NOT NULL
            )
        """.trimIndent())
    }
}
