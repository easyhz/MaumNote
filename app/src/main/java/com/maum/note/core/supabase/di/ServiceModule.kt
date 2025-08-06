package com.maum.note.core.supabase.di

import com.maum.note.core.supabase.service.user.service.UserService
import com.maum.note.core.supabase.service.user.service.UserServiceImpl
import com.maum.note.core.supabase.service.auth.AuthService
import com.maum.note.core.supabase.service.auth.AuthServiceImpl
import com.maum.note.core.supabase.service.board.service.comment.CommentService
import com.maum.note.core.supabase.service.board.service.comment.CommentServiceImpl
import com.maum.note.core.supabase.service.board.service.post.PostService
import com.maum.note.core.supabase.service.board.service.post.PostServiceImpl
import com.maum.note.core.supabase.service.note.service.NoteService
import com.maum.note.core.supabase.service.note.service.NoteServiceImpl
import com.maum.note.core.supabase.service.report.service.ReportService
import com.maum.note.core.supabase.service.report.service.ReportServiceImpl
import com.maum.note.core.supabase.service.tone.service.ToneService
import com.maum.note.core.supabase.service.tone.service.ToneServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {
    @Binds
    fun bindAuthService(
        authServiceImpl: AuthServiceImpl
    ): AuthService

    @Binds
    fun bindUserService(
        userServiceImpl: UserServiceImpl
    ): UserService

    @Binds
    fun bindNoteService(
        noteServiceImpl: NoteServiceImpl
    ): NoteService

    @Binds
    fun bindPostService(
        postServiceImpl: PostServiceImpl
    ): PostService
    
    @Binds
    fun bindCommentService(
        commentServiceImpl: CommentServiceImpl
    ): CommentService

    @Binds
    fun bindToneService(
        toneServiceImpl: ToneServiceImpl
    ): ToneService

    @Binds
    fun bindReportService(
        reportServiceImpl: ReportServiceImpl
    ): ReportService
}