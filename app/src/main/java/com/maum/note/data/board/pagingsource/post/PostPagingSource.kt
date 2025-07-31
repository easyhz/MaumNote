package com.maum.note.data.board.pagingsource.post

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maum.note.core.supabase.service.board.dto.post.PostWithCommentDto
import com.maum.note.data.board.datasource.remote.post.PostRemoteDataSource

class PostPagingSource(
    private val postRemoteDataSource: PostRemoteDataSource
) : PagingSource<Int, PostWithCommentDto>() {
    override fun getRefreshKey(state: PagingState<Int, PostWithCommentDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.run {
                prevKey?.plus(1) ?: nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostWithCommentDto> {
        return try {
            val page = params.key ?: START_PAGE
            val loadSize = params.loadSize

            val from = page * PAGE_SIZE
            val to = from + params.loadSize - 1L
            val data = postRemoteDataSource.fetchPosts(from = from.toLong(), to = to.toLong())
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