package com.maum.note.core.supabase.service.auth

import io.github.jan.supabase.auth.user.UserInfo

interface AuthService {
    suspend fun signInAnonymously()
    fun getCurrentUser(): UserInfo?
    suspend fun signOut()
    suspend fun clearSession()
}