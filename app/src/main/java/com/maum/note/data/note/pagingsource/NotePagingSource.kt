package com.maum.note.data.note.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maum.note.core.common.error.AppError
import com.maum.note.core.supabase.service.note.dto.NoteDto
import com.maum.note.data.board.pagingsource.post.PostPagingSource
import com.maum.note.data.note.datasource.remote.NoteRemoteDataSource
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource

class NotePagingSource(
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : PagingSource<Int, NoteDto>() {
    override fun getRefreshKey(state: PagingState<Int, NoteDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteDto> {
        return try {
            val user = userRemoteDataSource.getCurrentUser() ?: return LoadResult.Error(AppError.NoUserDataError)
            val page = params.key ?: START_PAGE
            val loadSize = params.loadSize

            val from = page * PostPagingSource.Companion.PAGE_SIZE
            val to = from + params.loadSize - 1L
            val data = noteRemoteDataSource.fetchNotes(userId = user.id, from = from.toLong(), to = to.toLong())
            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.size < loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        private const val START_PAGE = 0
    }
}