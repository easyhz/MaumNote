package com.maum.note.core.supabase.constant

sealed class Table(val name: String) {
    object USERS : Table("MN_USERS") {
        const val ID = "id"
        const val NICKNAME = "nickname"
        const val HAS_AGREED_TO_TERMS = "has_agreed_to_terms"
        const val STUDENT_AGE = "student_age"
        const val CREATION_TIME = "creation_time"
        const val IS_DELETED = "is_deleted"
    }

    object NOTES : Table("NOTES") {
        const val ID = "id"
        const val USER_ID = "user_id"
        const val CREATION_TIME = "creation_time"
        const val IS_DELETED = "is_deleted"
    }
}