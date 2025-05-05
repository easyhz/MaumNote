package com.maum.note.core.database.note.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.maum.note.core.database.note.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Query(
        """
        SELECT *
        FROM NOTE
        WHERE isDeleted = 0
        ORDER BY createdAt DESC
    """
    )
    fun findAllNotesPagingSource(): PagingSource<Int, NoteEntity>

    @Query(
        """
        SELECT *
        FROM NOTE
        WHERE isDeleted = 0
        ORDER BY createdAt DESC
    """
    )
    fun findAllNotesFlow(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Query("SELECT * FROM NOTE WHERE id = :id")
    suspend fun findNoteById(id: Long): NoteEntity

    @Transaction
    suspend fun insertAndGetNote(note: NoteEntity): NoteEntity {
        val id = insertNote(note)
        return findNoteById(id)
    }

}