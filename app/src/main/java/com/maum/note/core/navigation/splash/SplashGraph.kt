package com.maum.note.core.navigation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.maum.note.core.navigation.home.navigateToHome
import com.maum.note.core.navigation.onboarding.navigateToOnboarding
import com.maum.note.core.navigation.splash.screen.Splash
import com.maum.note.ui.screen.splash.SplashScreen


fun NavGraphBuilder.splashGraph(
    navController: NavController
) {
    composable<Splash> {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }
        SplashScreen(
            navigateToOnboarding = { navController.navigateToOnboarding(navOptions) },
            navigateToHome = { navController.navigateToHome(navOptions) },
        )
    }
}