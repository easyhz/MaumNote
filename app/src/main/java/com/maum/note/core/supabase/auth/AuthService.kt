package com.maum.note.core.supabase.auth

import io.github.jan.supabase.auth.user.UserInfo

interface AuthService {
    suspend fun signInAnonymously()
    fun getCurrentUser(): UserInfo?
}