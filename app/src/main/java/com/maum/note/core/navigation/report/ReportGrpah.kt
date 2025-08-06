package com.maum.note.core.navigation.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.maum.note.core.navigation.report.screen.Report
import com.maum.note.ui.screen.board.report.ReportScreen

fun NavGraphBuilder.reportGraph(
    navController: NavController
) {
    composable<Report>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "maum-note://report?postId={postId}&commentId={commentId}"
            }
        )
    ) {
        ReportScreen(
            navigateUp = navController::navigateUp,
        )
    }
}

fun NavController.navigateToReport(
    postId: String?,
    commentId: String?,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Report(
            postId = postId,
            commentId = commentId
        ),
        navOptions = navOptions
    )
}
