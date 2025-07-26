package com.maum.note.data.board.repository

import com.maum.note.core.common.error.AppError
import com.maum.note.core.model.board.Comment
import com.maum.note.core.model.board.Post
import com.maum.note.data.board.datasource.remote.comment.CommentRemoteDataSource
import com.maum.note.data.board.datasource.remote.post.PostRemoteDataSource
import com.maum.note.data.board.mapper.CommentMapper
import com.maum.note.data.board.mapper.PostMapper
import com.maum.note.data.user.datasource.remote.UserRemoteDataSource
import com.maum.note.domain.board.model.request.CreatePostRequest
import com.maum.note.domain.board.repository.BoardRepository
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val postMapper: PostMapper,
    private val commentMapper: CommentMapper,
    private val postRemoteDataSource: PostRemoteDataSource,
    private val commentRemoteDataSource: CommentRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
): BoardRepository {
    override suspend fun createPost(request: CreatePostRequest): Result<Unit> = runCatching {
        val userInfo = userRemoteDataSource.getCurrentUser() ?: throw AppError.NoUserDataError
        postRemoteDataSource.insertPost(postMapper.toPostDto(request = request, userId = userInfo.id))
    }

    override suspend fun fetchPosts(): Result<List<Post>> = runCatching {
        postRemoteDataSource.fetchPosts().map { postMapper.toPost(it) }
    }

    override suspend fun fetchPost(id: String): Result<Post> = runCatching {
        postRemoteDataSource.fetchPost(id).let { postMapper.toPost(it) }
    }

    override suspend fun fetchComments(postId: String): Result<List<Comment>> = runCatching {
        commentRemoteDataSource.fetchComments(postId).map { commentMapper.toComment(it) }
    }
}