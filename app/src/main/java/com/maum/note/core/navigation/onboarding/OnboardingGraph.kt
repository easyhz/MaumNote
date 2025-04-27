package com.maum.note.core.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.maum.note.core.navigation.home.navigateToHome
import com.maum.note.core.navigation.onboarding.screen.Onboarding
import com.maum.note.ui.screen.onboarding.age.OnboardingAgeScreen
import com.maum.note.ui.screen.onboarding.start.OnboardingStartScreen
import com.maum.note.ui.screen.onboarding.tone.OnboardingToneScreen

fun NavGraphBuilder.onboardingGraph(
    navController: NavController
) {
    navigation<Onboarding>(
        startDestination = Onboarding.Start,
    ) {
        composable<Onboarding.Start> {
            OnboardingStartScreen()
        }

        composable<Onboarding.Age> {
            OnboardingAgeScreen(
                navigateUp = navController::navigateUp,
                navigateToNext = { navController.navigateToOnboardingTone() }
            )
        }

        composable<Onboarding.Tone> {
            val navOptions = navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }

            OnboardingToneScreen(
                navigateUp = navController::navigateUp,
                navigateToNext = { navController.navigateToHome(navOptions = navOptions) }
            )
        }
    }
}

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(route = Onboarding, navOptions = navOptions)
}

fun NavController.navigateToOnboardingAge(navOptions: NavOptions? = null) {
    navigate(route = Onboarding.Age, navOptions = navOptions)
}

fun NavController.navigateToOnboardingTone(navOptions: NavOptions? = null) {
    navigate(route = Onboarding.Tone, navOptions = navOptions)
}