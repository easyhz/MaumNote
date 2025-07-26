package com.maum.note.data.board.di

import com.maum.note.core.common.helper.resource.ResourceHelper
import com.maum.note.data.board.mapper.CommentMapper
import com.maum.note.data.board.mapper.PostMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object BoardMapperModule {
    @Provides
    fun providePostMapper(
        resourceHelper: ResourceHelper,
    ): PostMapper {
        return PostMapper(
            resourceHelper = resourceHelper,
        )
    }

    @Provides
    fun provideCommentMapper(
        resourceHelper: ResourceHelper,
    ): CommentMapper {
        return CommentMapper(
            resourceHelper = resourceHelper,
        )
    }
}