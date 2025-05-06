package com.maum.note.core.firebase.util

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.maum.note.core.common.error.AppError
import kotlinx.coroutines.tasks.await

const val TAG = "ResponseHandler"

/**
 * collection 에서 원하는 document 를 받는 함수
 *
 * @param execute [DocumentSnapshot] 파이어스토어 쿼리문이 들어옴.
 */
internal suspend inline fun <reified T> documentHandler(
    crossinline execute: () -> Task<DocumentSnapshot>
): Result<T> =
    runCatching {
        val result = execute().await().toObject(T::class.java)
        result ?: throw AppError.NoResultError
    }.fold(
        onSuccess = {  Result.success(it) },
        onFailure = { e -> handleException(e, "document") }
    )

private fun <T> handleException(e: Throwable, tag: String): Result<T> {
    return when(e) {
        is FirebaseFirestoreException -> { handleFireStoreException(e, tag) }
        is AppError.NoResultError -> { handleNoResultException(e, tag) }
        else -> { handleGeneralException(e, tag) }
    }
}

private fun <T> handleFireStoreException(e: FirebaseFirestoreException, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - FireStore: ${e.message}", e)
    return Result.failure(e)
}

private fun <T> handleNoResultException(e: AppError.NoResultError, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - NoResult: ${e.message}")
    return Result.failure(AppError.NoResultError)
}

private fun <T> handleGeneralException(e: Throwable, tag: String): Result<T> {
    Log.e(TAG, "In $tag handler - Exception: ${e.message}", e)
    return Result.failure(AppError.UnexpectedError)
}