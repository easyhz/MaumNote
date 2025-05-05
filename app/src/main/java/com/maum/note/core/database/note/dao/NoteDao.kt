package com.maum.note.core.database.note.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
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

}