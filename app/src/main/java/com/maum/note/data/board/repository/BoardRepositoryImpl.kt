package com.maum.note.data.board.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.maum.note.core.common.error.AppError
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.data.board.datasource.local.board.BoardLocalDataSource
import com.maum.note.data.board.datasource.remote.comment.CommentRemoteDataSource
import com.maum.note.data.board.datasource.remote.post.PostRemoteDataSource
import com.maum.note.data.board.mapper.CommentMapper
import com.maum.note.data.board.mapper.PostMapper
import com.maum.note.data.board.pagingsource.post.PostPagingSource
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.domain.board.model.comment.request.CreateCommentRequest
import com.maum.note.domain.board.model.post.request.CreatePostRequest
import com.maum.note.domain.board.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val postMapper: PostMapper,
    private val commentMapper: CommentMapper,
    private val postRemoteDataSource: PostRemoteDataSource,
    private val commentRemoteDataSource: CommentRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val boardLocalDataSource: BoardLocalDataSource,
): BoardRepository {
    override suspend fun createPost(request: CreatePostRequest): Result<Unit> = runCatching {
        val userInfo = userRemoteDataSource.getCurrentUser() ?: throw AppError.NoUserDataError
        postRemoteDataSource.insertPost(postMapper.toPostDto(request = request, userId = userInfo.id))
    }

    override fun fetchPagedPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PostPagingSource.PAGE_SIZE,
                initialLoadSize = PostPagingSource.PAGE_SIZE,
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    postRemoteDataSource = postRemoteDataSource,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { postDto ->
                postMapper.toPost(postDto)
            }
        }
    }

    override suspend fun fetchPosts(): Result<List<Post>> = runCatching {
        postRemoteDataSource.fetchPosts(0, 10).map { postMapper.toPost(it) }
    }

    override suspend fun fetchPost(id: String): Result<Post> = runCatching {
        postRemoteDataSource.fetchPost(id).let { postMapper.toPost(it) }
    }

    override suspend fun deletePost(id: String): Result<Unit> = runCatching {
        postRemoteDataSource.deletePost(id)
    }

    override suspend fun createComment(request: CreateCommentRequest): Result<Unit> = runCatching {
        val userInfo = userRemoteDataSource.getCurrentUser() ?: throw AppError.NoUserDataError
        commentRemoteDataSource.insertComment(commentMapper.toCommentDto(request = request, userId = userInfo.id))
    }

    override suspend fun fetchComments(postId: String): Result<List<Comment>> = runCatching {
        commentRemoteDataSource.fetchComments(postId).map { commentMapper.toComment(it) }
    }

    override suspend fun deleteComment(id: String): Result<Unit> = runCatching {
        commentRemoteDataSource.deleteComment(id)
    }

    override fun getAnonymousSettingFlow(): Flow<Boolean> {
        return boardLocalDataSource.getAnonymousSettingFlow()
    }

    override suspend fun setAnonymousSetting(isAnonymous: Boolean): Result<Unit> = runCatching {
        boardLocalDataSource.setAnonymousSetting(isAnonymous)
    }
}