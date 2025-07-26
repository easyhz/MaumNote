package com.maum.note.core.navigation.board

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.maum.note.core.common.util.url.urlEncode
import com.maum.note.core.designSystem.util.transition.SlideDirection
import com.maum.note.core.designSystem.util.transition.exitSlide
import com.maum.note.core.navigation.board.screen.Board
import com.maum.note.core.navigation.board.screen.PostCreation
import com.maum.note.core.navigation.board.screen.PostDetail
import com.maum.note.core.navigation.setting.navigateToSetting
import com.maum.note.ui.screen.board.board.BoardScreen
import com.maum.note.ui.screen.board.post.creation.PostCreationScreen
import com.maum.note.ui.screen.board.post.detail.PostDetailScreen


fun NavGraphBuilder.boardGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable<Board>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        BoardScreen(
            modifier = modifier,
            navigateToSetting = navController::navigateToSetting,
            navigateToCreation = navController::navigateToPostCreation,
            navigateToPostDetail = navController::navigateToPostDetail
        )
    }

    composable<PostCreation> {
        PostCreationScreen(
            navigateUp = navController::navigateUp
        )
    }

    composable<PostDetail>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "maum-note://board/detail?id={id}&title={title}"
            }
        )
    ) {
        PostDetailScreen(
            navigateUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToBoard(navOptions: NavOptions? = null) {
    navigate(route = Board, navOptions = navOptions)
}

fun NavController.navigateToPostCreation(navOptions: NavOptions? = null) {
    navigate(route = PostCreation, navOptions = navOptions)
}


fun NavController.navigateToPostDetail(
    id: String,
    title: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = PostDetail(
            id = id,
            title = title.urlEncode()
        ), navOptions = navOptions
    )
}