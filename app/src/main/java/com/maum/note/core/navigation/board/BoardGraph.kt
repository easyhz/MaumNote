package com.maum.note.core.navigation.board

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maum.note.core.designSystem.util.transition.SlideDirection
import com.maum.note.core.designSystem.util.transition.exitSlide
import com.maum.note.core.navigation.board.screen.Board
import com.maum.note.core.navigation.setting.navigateToSetting
import com.maum.note.ui.screen.board.board.BoardScreen


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
        )
    }
}

fun NavController.navigateToBoard(navOptions: NavOptions? = null) {
    navigate(route = Board, navOptions = navOptions)
}