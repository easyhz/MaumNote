package com.maum.note.core.database.tone.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.maum.note.core.database.tone.entity.ToneEntity
import java.time.LocalDateTime

@Dao
interface ToneDao {

    @Upsert
    suspend fun upsertTone(tone: ToneEntity)

    @Query(
        """
        SELECT *
        FROM TONE
        WHERE isSelected = 1
        ORDER BY createdAt DESC
    """
    )
    fun findAllSelectedTones(): List<ToneEntity>

    @Query(
        """
        SELECT *
        FROM TONE
        WHERE noteType = :noteType
          AND isSelected = 1
        ORDER BY createdAt DESC
        LIMIT 1
    """
    )
    fun findByNoteType(noteType: String): ToneEntity?

    @Transaction
    suspend fun updateTone(noteType: String, content: String) {
        val tone = findByNoteType(noteType)

        val toneEntity = tone?.copy(content = content, updatedAt = LocalDateTime.now().toString())
            ?: ToneEntity(
                noteType = noteType,
                content = content,
                isSelected = true,
                createdAt = LocalDateTime.now().toString(),
                updatedAt = LocalDateTime.now().toString()
            )

        upsertTone(toneEntity)
    }

}