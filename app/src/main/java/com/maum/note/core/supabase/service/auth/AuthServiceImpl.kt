package com.maum.note.core.supabase.service.auth

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.user.UserInfo
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: Auth
): AuthService {

    override suspend fun signInAnonymously() {
        auth.signInAnonymously()
    }

    override fun getCurrentUser(): UserInfo? {
        return auth.currentUserOrNull()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun clearSession() {
        auth.clearSession()
    }
}
