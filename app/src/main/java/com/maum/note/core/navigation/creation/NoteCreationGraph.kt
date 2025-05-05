package com.maum.note.core.navigation.creation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.maum.note.core.model.note.NoteType
import com.maum.note.core.navigation.creation.screen.NoteCreation
import com.maum.note.ui.screen.note.creation.content.NoteContentScreen
import com.maum.note.ui.screen.note.creation.select.NoteTypeSelectionScreen

fun NavGraphBuilder.noteCreationGraph(
    navController: NavController
) {
    navigation<NoteCreation>(
        startDestination = NoteCreation.NoteTypeSelection,
    ) {
        composable<NoteCreation.NoteTypeSelection> {
            NoteTypeSelectionScreen(
                navigateUp = navController::navigateUp,
                navigateToNext = navController::navigateToNoteContent,
            )
        }

        composable<NoteCreation.NoteContent> {
             NoteContentScreen(
                 navigateUp = navController::navigateUp,
                 navigateToNext = navController::navigateToNoteGeneration,
             )
        }

        composable<NoteCreation.NoteGeneration> {  }

    }
}

fun NavController.navigateToNoteCreation(navOptions: NavOptions? = null) {
    navigate(route = NoteCreation, navOptions = navOptions)
}

fun NavController.navigateToNoteContent(noteType: NoteType, navOptions: NavOptions? = null) {
    navigate(route = NoteCreation.NoteContent(noteType = noteType.name), navOptions = navOptions)
}

fun NavController.navigateToNoteGeneration(navOptions: NavOptions? = null) {
    navigate(route = NoteCreation.NoteGeneration, navOptions = navOptions)
}