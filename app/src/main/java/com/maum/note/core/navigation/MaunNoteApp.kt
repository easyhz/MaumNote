package com.maum.note.core.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maum.note.core.designSystem.util.transition.SlideDirection
import com.maum.note.core.designSystem.util.transition.enterSlide
import com.maum.note.core.designSystem.util.transition.exitSlide
import com.maum.note.core.navigation.home.homeGraph
import com.maum.note.core.navigation.onboarding.onboardingGraph
import com.maum.note.core.navigation.splash.screen.Splash
import com.maum.note.core.navigation.splash.splashGraph

@Composable
fun MaumNoteApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier
            .systemBarsPadding(),
        navController = navController,
        startDestination = Splash,
        enterTransition = { enterSlide(SlideDirection.Start) },
        exitTransition = { exitSlide(SlideDirection.Start) },
        popEnterTransition = { enterSlide(SlideDirection.End) },
        popExitTransition = { exitSlide(SlideDirection.End) }
    ) {
        splashGraph(navController = navController)
        onboardingGraph(navController = navController)
        homeGraph(navController = navController)
    }
}