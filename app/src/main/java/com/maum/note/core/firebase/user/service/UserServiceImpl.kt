package com.maum.note.core.firebase.user.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.maum.note.core.common.error.AppError
import com.maum.note.core.firebase.constant.Collection
import com.maum.note.core.firebase.user.model.request.SaveUserRequest
import com.maum.note.core.firebase.user.model.response.UserResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): UserService {
    override fun isLogin(): Boolean {
        return auth.currentUser != null
    }
    override fun getUserId(): String {
        return auth.currentUser?.uid ?: throw AppError.UnexpectedError
    }
    override suspend fun getUser(uid: String): UserResponse? {
        return firestore.collection(Collection.USERS).document(uid).get().await().toObject(UserResponse::class.java)
    }

    override suspend fun signInAnonymously(): String {
        return auth.signInAnonymously().await().user?.uid ?: throw AppError.UnexpectedError
    }

    override suspend fun saveUser(saveUserRequest: SaveUserRequest) {
        firestore.collection(Collection.USERS).document(saveUserRequest.id).set(saveUserRequest).await()
    }
}