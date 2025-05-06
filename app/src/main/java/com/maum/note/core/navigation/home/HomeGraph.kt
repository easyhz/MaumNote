package com.maum.note.core.navigation.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maum.note.core.designSystem.util.transition.SlideDirection
import com.maum.note.core.designSystem.util.transition.exitSlide
import com.maum.note.core.navigation.note.creation.navigateToNoteCreation
import com.maum.note.core.navigation.home.screen.Home
import com.maum.note.core.navigation.note.detail.navigateToNoteDetail
import com.maum.note.core.navigation.setting.navigateToSetting
import com.maum.note.ui.screen.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    composable<Home>(
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        HomeScreen(
            navigateToSetting = navController::navigateToSetting,
            navigateToCreation = navController::navigateToNoteCreation,
            navigateToDetail = navController::navigateToNoteDetail
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = Home, navOptions = navOptions)
}