package com.maum.note.core.supabase.di

import com.maum.note.core.supabase.service.user.service.UserService
import com.maum.note.core.supabase.service.user.service.UserServiceImpl
import com.maum.note.core.supabase.service.auth.AuthService
import com.maum.note.core.supabase.service.auth.AuthServiceImpl
import com.maum.note.core.supabase.service.note.service.NoteService
import com.maum.note.core.supabase.service.note.service.NoteServiceImpl
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
}