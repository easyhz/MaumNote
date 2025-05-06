package com.maum.note.core.navigation.note.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.maum.note.core.common.util.url.urlEncode
import com.maum.note.core.model.note.Note
import com.maum.note.core.navigation.note.detail.screen.NoteDetail
import com.maum.note.ui.screen.note.detail.NoteDetailScreen

fun NavGraphBuilder.noteDetailGraph(
    navController: NavController
) {
    composable<NoteDetail>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "maum-note://note/detail?id={id}&noteType={noteType}&ageType={ageType}&sentenceCountType={sentenceCountType}&inputContent={inputContent}&result={result}&createdAt={createdAt}"
            }
        )
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
    navigate(
        route = NoteDetail(
            id = note.id,
            noteType = note.noteType.name,
            ageType = note.ageType.name,
            sentenceCountType = note.sentenceCountType.name,
            inputContent = note.inputContent.urlEncode(),
            result = note.result.urlEncode(),
            createdAt = note.createdAt.toString(),
        ), navOptions = navOptions
    )
}
