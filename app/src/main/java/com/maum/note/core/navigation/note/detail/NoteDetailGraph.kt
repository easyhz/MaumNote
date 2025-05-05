package com.maum.note.core.navigation.note.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maum.note.core.model.note.Note
import com.maum.note.core.navigation.note.detail.screen.NoteDetail
import com.maum.note.core.navigation.note.detail.screen.toArgs
import com.maum.note.ui.screen.note.detail.NoteDetailScreen

fun NavGraphBuilder.noteDetailGraph(
    navController: NavController
) {
    composable<NoteDetail>(
        typeMap = NoteDetail.typeMap,
    ) {
        NoteDetailScreen(
            navigateUp = navController::navigateUp,
        )
    }
}

fun NavController.navigateToNoteDetail(
    note: Note,
    navOptions: NavOptions? = null
) {
    navigate(route = NoteDetail(noteDetailArgs = note.toArgs()), navOptions = navOptions)
}
